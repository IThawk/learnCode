# 防火墙

# 停止firewall
systemctl stop firewalld

# 禁止firewall的开机自启动
systemctl disable firewalld

iptables -A INPUT -p tcp -dport 3306 -j ACCEPT
# 添加3306端口