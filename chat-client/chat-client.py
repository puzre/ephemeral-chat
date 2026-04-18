#!/home/canary/venv/bin/python3
import argparse
from websockets.asyncio.client import connect
import asyncio

parser = argparse.ArgumentParser()

parser.add_argument('--username', default='unknown')
parser.add_argument('--url', required=True)

args = parser.parse_args()

class ChatClient:
    def __init__(self, url, username):
        self.url = url + "/" + username + "/ephemeral-chat"
        self.username = username

    def print_prompt(self):
        print(f"[{self.username}] ", end="", flush=True)

    async def send_msg(self, ws):
        loop = asyncio.get_event_loop()
        while True:
            msg = await loop.run_in_executor(None, input)
            try:
                msg.encode('utf-8')
            except UnicodeEncodeError:
                msg = msg.encode('utf-8', 'ignore').decode('utf-8')
            if msg.strip().lower() in ("/exit"):
                print("disconnecting...")
                break
            await ws.send(msg)
            print(f"\033[A\r\033[K[{self.username}] {msg}")
            self.print_prompt()

    async def receive_msg(self, ws):
        try:
            while True:
                msg = await ws.recv()
                print(f"\r\033[K{msg}")clear
                self.print_prompt()
        except asyncio.CancelledError:
            pass

    async def run(self):
        async with connect(self.url) as ws:
            print("connected to ", self.url)
            print("use '/exit' to disconnect")

            receive_task = asyncio.create_task(self.receive_msg(ws))
            self.print_prompt()
            await self.send_msg(ws)
            receive_task.cancel()
            try:
                await receive_task
            except asyncio.CancelledError:
                pass

async def main():
    client = ChatClient(args.url, args.username)
    await client.run()

asyncio.run(main())