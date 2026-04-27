# Ansible Setup and Playbook Guide

## Quick Reference for Ansible Installation and Basic Playbook

### 1. Install Ansible
```bash
sudo apt update
sudo apt install ansible -y
```

### 2. Verify Installation
```bash
ansible --version
```

### 3. Install OpenSSH Server
```bash
sudo apt install openssh-server -y
```

### 4. Generate SSH Key
```bash
ssh-keygen
```

### 5. Create Inventory File
Create a file named `hosts`:
```bash
nano hosts
```

Add the following content:
```ini
[local]
localhost ansible_connection=local
```

### 6. Test Connection
```bash
ansible -i hosts all -m ping
```

### 7. Create Ping Playbook
Create a file named `ping.yml`:
```bash
nano ping.yml
```

Add the following content:
```yaml
- name: Ping localhost
  hosts: all
  tasks:
    - name: Test ping
      ping:
```

### 8. Run the Playbook
```bash
ansible-playbook -i hosts ping.yml
```

## Notes
- The `ansible_connection=local` parameter allows Ansible to run commands locally without SSH
- The ping module tests connectivity to managed nodes
- Playbooks are written in YAML format and define automation tasks
