# 项目名称

---

## 目录

1. 项目简介
2. 项目使用效果图
3. 安装说明
4. 使用说明
6. 关于作者
10. 更新日志

---

## 项目简介
由springboot+vue开发的前后端分离后台管理系统。



## 效果图

![](F:\GitTest\travel_system_git\image\login.png)

![](F:\GitTest\travel_system_git\image\main.png)

![](F:\GitTest\travel_system_git\image\user.png)

![](F:\GitTest\travel_system_git\image\order.png)



## 安装说明

### 环境依赖
IDEA——2019.3.3

JDK——1.8+

Maven——3.6.3

MySQL——8.0.16

SpringBoot——2.1.6

Mybatis Plus——3.3.1.tmp

pageHelper

shiro

RabbitMQ

Redis

Bootstrap

Vue

### 部署安装
1. #### **修改hosts**
   

hosts目录：C:\Windows\System32\drivers\etc，注意！hosts没有后缀！
    
    直接把下面两行粘贴到最底下
    
    ```
    127.0.0.1 www.origami.com
    127.0.0.1 api.origami.com
    ```


​    
​    
    **注意**：
    
    如果发现不能修改或修改后不能保存，直接把hosts文件的内容复制一份，然后删了这个文件，自己重新创建一个hosts再把原文件的内容粘贴进去即可（高效快捷~）


​    
​    
    **图解**：

![](https://github.com/Origami-An/travel_system_project.git/image/hosts1.png)
    ![](F:\GitTest\travel_system_git\image\hosts2.png)



2. #### **nginx安装**

    解压已经配好的nginx，解压后运行

    

    **注意：**

    运行路径不能有中文！建议解压到全英文路径下运行nginx！

    如果使用命令运行需要在nginx根目录下使用命令

    

    **运行方式**：

    直接双击nginx.exe
    或者通过cmd，在当前目录下打开 输入start nginx

    

    **nginx命令：**

    ```
    start nginx  启动
    ```

    ```
    nginx -s reload  刷新
    ```

    ```
    nginx -s stop  停止
    ```

    ```
    taskkill /IM  nginx.exe /F  强行关闭nginx进程
    ```

    ```
    tasklist /fi "imagename eq nginx.exe"  查看nginx进程
    ```

    

3. #### 前端部署

    前端环境我已经集成好了

    通过live server启动项目

    在idea最底下的工具栏Terminal命令窗口输入 ：

    ```
    live-server --port=9003
    ```

    ![](F:\GitTest\travel_system_git\image\up.png)

    

4. #### 后端部署

    后端为springboot工程，直接运行启动类

    


## 使用说明
### 简要说明
RabbiMQ和nginx，本人是放在linux上的。

如果需要请自行修改后端工程的application.yml的对应信息！

如果没有安装，请先安装在运行项目！




## 关于作者
作者：origami

联系方式：/


## 更新日志
### V1.0.0 版本，2020-03-04
1. 首次发布
