# Learnease - 在线教学网站

JDK版本： jdk1.8.0_141   openjdk1.8.0.151
服务器系统：CentOS 7
数据库： Mysql 5.7
```
### 项目运行前的准备：

* #### 安装/配置
```
>CentOS 安装第三方源：
epel-release
Nux Dextop

>安装在线编译环境(yum)：
1. 安装PHP： 
php-devel php-cli php-commmon php-fpm hp-gd php-pecl-memcache php-mysql php-pear php-mbstring php-snmp php-xml php-pspell php-pdo php-mcrypt

2. 安装C、C++编译工具： 
gcc-c++ gcc

3.安装python:
python-qrcode-core python-di python-dmidecode python-inotify python-sss-murmur libselinux-python python-dateutil python2-caribou python-rtslib
python-ethtool python-urlgrabber python-netaddr python-configshell python-custodia python-pycparser python-linux-procfs python-backports
python-urwid python-iniparse python-schedutils python2-crypto python2-html2text python2-cryptography python-libipa_hbac python-dns
python-augeas python-ntplib python-coverage python-mako python-krbV python-requests python-setproctitle gnome-python2 python-ldap
libreport-python  python-yubico  python-kitchen  python-decorator python-IPy python-blivet python-gssapi
python-nss python-netifaces python-cups python python-kerberos python-meh python-firewall
python-paramiko python-slip-dbus  python-javapackages python-sssdconfig python-deltarpm libuser-python python2-pyasn1 python-pwquality
python-ply  python-perf python-brlapi python-backports-ssl_match_hostname python-pyblock policycoreutils-python

>Tomcat设置：
	tomcat的bin目录下添加setenv.sh文件，配置java环境：
	   export JAVA_HOME=/usr/java/jdk1.8.0_141
```

* #### 运行PC^2软件

```
>PC^2初始化：
  站点: site1 site1     密码：site1
  默认ADMINISTRATOR角色  用户名：administrator1 密码administrator1
  JUDGE角色       用户名：judge1 密码：judge1

  用ADMINISTRATOR角色添加编程语言
  
  另外： 复制pc2v9.ini文件到tomcat的bin目录下
  
>无界面运行PC^2服务:
  ./pc2server --nogui --contestpassword site1 --login site1 --password site1
  不挂断地在后台运行:
  nohup ./pc2server --nogui --contestpassword site1 --login site1 --password site1 &

```
* ##### 运行MySql数据库

```
用户名： uncle  密码： uncle
数据库的字符集统一设置成utf8,本项目使用的数据库名为Learnease
```

### 其他说明
```

```



2017.04.07      完成编程题后台代码/测试页面在WebRoot/test目录

2017.04.19      初步完成用户登陆部分

2017.07.01      完成ResourceManagePage目录下的myPapers.html,paperList.html子页面

2017.07.05      完成‘创建试卷’功能模块页面

2017.10.02      完成在线编译后台代码

2018.04.01      使用spring boot重构题库模块
