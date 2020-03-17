ansible-playbook play-book-certificaes.yml -i hosts.yml 
ansible qa_lacastellana -a sudo /bin/cat /etc/sudoers -i hosts.yml
ansible all -m ping -i hosts.yml 
