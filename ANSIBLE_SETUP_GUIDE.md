# Ansible Setup and Playbook Guide

## Quick Reference for Ansible Installation, Ping Playbook and Web Application Deployment

---

## 1. Install Ansible
```bash
sudo apt update
sudo apt install ansible -y
```

## 2. Verify Installation
```bash
ansible --version
```

## 3. Install OpenSSH Server
```bash
sudo apt install openssh-server -y
```

## 4. Generate SSH Key
```bash
ssh-keygen
```

## 5. Create Inventory File
Create file:
```bash
nano hosts
```

Add:
```ini
[local]
localhost ansible_connection=local
```

Save:
```
CTRL + X
Y
Enter
```

## 6. Test Connection
```bash
ansible -i hosts all -m ping
```

Expected Output:
```
localhost | SUCCESS => {
    "ping": "pong"
}
```

---

## Ping Playbook

## 7. Create Ping Playbook
```bash
nano ping.yml
```

Add:
```yaml
---
- name: Ping localhost
  hosts: all
  tasks:
    - name: Test ping
      ping:
```

## 8. Run Ping Playbook
```bash
ansible-playbook -i hosts ping.yml
```

---

## Deploy a Simple Web Application

## 9. Create Web Server Playbook
```bash
nano webserver.yml
```

Add:
```yaml
---
- name: Install and Configure Apache Web Server
  hosts: all
  become: yes
  
  tasks:
    - name: Install Apache
      apt:
        name: apache2
        state: present
        update_cache: yes
    
    - name: Start Apache Service
      service:
        name: apache2
        state: started
        enabled: yes
    
    - name: Deploy Web Page
      copy:
        content: "<h1>Hello from Ansible Web Server</h1>"
        dest: /var/www/html/index.html
```

Save:
```
CTRL + X
Y
Enter
```

## 10. Run Web Playbook
```bash
ansible-playbook -i hosts webserver.yml
```

## 11. Verify Output
Open browser:
```
http://localhost
```

Expected Output:
```
Hello from Ansible Web Server
```

---

## Important Commands

### Check Apache Status
```bash
sudo systemctl status apache2
```

### Restart Apache
```bash
sudo systemctl restart apache2
```

### Stop Apache
```bash
sudo systemctl stop apache2
```

---

## Notes
- The `ansible_connection=local` parameter allows Ansible to run commands locally without SSH
- The ping module tests connectivity to managed nodes
- Playbooks are written in YAML format and define automation tasks
- The `become: yes` directive runs tasks with sudo privileges
- Apache2 is a popular web server for hosting web applications
