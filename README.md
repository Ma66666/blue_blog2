## Blue_blog
演示地址：[https://www.bilibili.com/video/BV12X4y1X7Xv/?vd_source=ff765505d9632c291d84b431b9cfb4df](https://www.bilibili.com/video/BV1LL41167AL/)


#### 介绍

我的毕业设计，我总共把他分为7个模块

- 用户模块主要包含：用户登录、注册、退出登录、查看用户信息、修改用户信息、修改密码、发送手机验证码等。
- 博客模块包含：博客的发布、删除、修改、查看、分享、收藏和点赞。
- 评论模块包含：评论文章、评论说说、回复评论、查看评论。
- 说说模块包含：发表说说、查看说说、删除说说、修改说说。
- 标签模块包含：记录用户标签，记录文章标签
- 排行榜模块包含：博客的热度排行、浏览量排行
- 私信模块:用websocket实现聊天室
- 搜索模块：elasticsearch搜索引擎实现搜索功能



#### 技术栈

##### 前端

- Vue
- 脚手架Vue-cli
- 组件库：element-ui



后端

- SpringBoot框架
- 数据访问层：Mybatis
- 数据库：Mysql，Redis
- 服务器：内置Tomcat （云服务器学生机性能拉跨，自己用得不堪入目就不贴地址了）
- 图片上传：七牛云存储
- 短信服务：腾讯云短信
- 富文本编辑：WangEditor
- 身份验证：JWT



#### 建议版本

- Jdk1.8
- Mysql5.7
- Maven 3.6.1
- Redis随便

#### 快速运行

1. 克隆代码
2. application.properties添加自己的云短信配置，七牛云配置，（配置有问题也可以私信我）
3. 需要数据库表结构可以私信我



#### 目录结构
http://qnimage.yxwhzj6.top/35Y%40SLOGCX7F3~U%253JGWG67.png


