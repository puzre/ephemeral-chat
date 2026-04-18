FROM alpine:3.20

ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

RUN apk add --no-cache openssh && \
    mkdir -p /run/sshd && \
    adduser -D canary && \
    passwd -u canary && \
    mkdir -p /home/canary/.ssh && \
    chmod 755 /home/canary && \
    chmod 700 /home/canary/.ssh && \
    sed -i 's/^#PubkeyAuthentication yes/PubkeyAuthentication yes/' /etc/ssh/sshd_config && \
    sed -i 's/^#PasswordAuthentication yes/PasswordAuthentication no/' /etc/ssh/sshd_config && \
    echo "PasswordAuthentication no" >> /etc/ssh/sshd_config && \
    echo "PubkeyAuthentication yes" >> /etc/ssh/sshd_config && \
    ssh-keygen -A

COPY --chown=canary:canary ssh-keys/canary-ssh-key.pub /home/canary/.ssh/authorized_keys
RUN chmod 600 /home/canary/.ssh/authorized_keys && \
    chown -R canary:canary /home/canary/.ssh

RUN apk add --no-cache python3 py3-pip py3-virtualenv && \
    python3 -m venv /home/canary/venv && \
    /home/canary/venv/bin/pip install --no-cache-dir websockets && \
    chown -R canary:canary /home/canary/venv

COPY --chown=canary:canary chat-client.py /home/canary/chat-client.py
RUN chmod +x /home/canary/chat-client.py

EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]