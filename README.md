# Server-Status
不知道取什么名字好，就直接叫这个了。功能如其名，就是发送服务器状态的一个App，使用的是JAVA语言，所有支持多平台（Linux、Windows和Unix）。

**实现的功能**
- 可以定时发送很多服务器的信息。通过邮件发送，使用的是465端口，在阿里云服务器上也能发邮件（阿里云自动屏蔽25端口的邮件，用25就发不出去邮件了）。
- 可以通过浏览器查看详细信息，支持在浏览器设置参数（比如邮箱收件人、定多长时间发送一次邮件、或者CPU温度达到多少时发送提醒邮件等等）。

**使用的技术**
- 后端：使用springboot集成mybatis、springmvc，以及springboot内嵌的Tomcat。
- 前端：采用web是Thymeleaf模板引擎，html email也是采用Thymeleaf模板引擎，邮件与网页都是响应式布局。
- 数据库：使用的是 H2 内嵌式数据库，一个2m大小的jar包，不用安装，随项目一起部署。对于这个很小很小的项目完全够用了。也不需要另外安装Mysql，消灭了一些可能出现的异常。
- 硬件信息收集：使用的是开源项目Oshi、和Jsensor（仅Windows的CPU温度收集），也当然用了JDK自带的一些api。

**提升**

这是我的第一个项目，大概是3月初开始的，到现在有1个多月了。应该算很慢的吧，不过springboot是现学现用的，中间踩了很多很多坑。比如因为一个“log4j”编译一直失败，花了一天才弄明白为什么；或者H2无法建表（在mysql上试都是可以的...H2就是无法建表）；以及制作html邮件的坑等等。所有能把这个项目做完还是不错了~

**展示**

*登录*
![image.png](http://www.xieetian.cn/upload/2020/4/image-947a3c41d76e46bfa649fcd26b5de693.png)

*注册*
![image.png](http://www.xieetian.cn/upload/2020/4/image-63ea94ef05d645418ce75170691b36d9.png)

*首页*
![image.png](http://www.xieetian.cn/upload/2020/4/image-a12c8b4010a847e9a3928f0da39007b8.png)

*详情页*
![image.png](http://www.xieetian.cn/upload/2020/4/image-f7d37d9979884ec1851b78c0b0dc75e7.png)

*折线图*
![image.png](http://www.xieetian.cn/upload/2020/4/image-f572fed63ac042b6adc4bd0b2b3dc532.png)

*设置页*
![image.png](http://www.xieetian.cn/upload/2020/4/image-a11c1d8811b741a0ae07f0085d635278.png)

*温度监控&定时消息页*
![image.png](http://www.xieetian.cn/upload/2020/4/image-0992a669a187489487579522ffde6ccf.png)

未写完，待续...
