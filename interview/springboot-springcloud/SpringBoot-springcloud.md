

# 什么是 Spring Boot？ 

多年来，随着新功能的增加，spring 变得越来越复杂。只需访问 https://spring.io/projects

页面，我们就会看到可以在我们的应用程序中使用的所有 Spring 项目的不同功能。如果必须启动一个新的 Spring 项目，我们必须添加构建路径或添加 Maven 依赖关系，配置应用程序服务器，添加 spring 配置。因此，开始一个新的 spring 项目需要很多努力，因为我们现在必须从头开始做所有事情。 

Spring   Boot 是解决这个问题的方法。Spring     Boot 已经建立在现有 spring 框架之上。使用 spring 启动，我们避免了之前我们必须做的所有样板代码和配置。因此，Spring  Boot 可以帮助我们以最少的工作量，更加健壮地使用现有的 Spring 功能。 

# Spring Boot 有哪些优点？ 

Spring Boot 的优点有： 

减少开发，测试时间和努力。 

使用 JavaConfig 有助于避免使用 XML。 

避免大量的 Maven 导入和各种版本冲突。 

提供意见发展方法。 

通过提供默认值快速开始开发。 

没有单独的 Web 服务器需要。这意味着你不再需要启动 Tomcat，Glassfish 或其他任何东西。 

需要更少的配置 因为没有 web.xml 文件。只需添加用@ Configuration 注释的类，然后添加用@Bean 注释的方法，Spring 将自动加载对象并像以前一样对其进行管理。您甚至可以将

@Autowired 添加到 bean 方法中，以使 Spring 自动装入需要的依赖关系中。 

基于环境的配置 使用这些属性，您可以将您正在使用的环境传递到应用程序：Dspring.profiles.active = {enviornment}。在加载主应用程序属性文件后，Spring 将在（application{environment} .properties）中加载后续的应用程序属性文件。 

# 什么是 JavaConfig？ 

 

Spring JavaConfig 是 Spring 社区的产品，它提供了配置 Spring IoC 容器的纯 Java 方法。因此它有助于避免使用 XML 配置。使用 JavaConfig 的优点在于： 

面向对象的配置。由于配置被定义为 JavaConfig 中的类，因此用户可以充分利用 Java 中的面向对象功能。一个配置类可以继承另一个，重写它的@Bean 方法等。 

减少或消除 XML 配置。基于依赖注入原则的外化配置的好处已被证明。但是，许多开发人员不希望在 XML 和 Java 之间来回切换。JavaConfig 为开发人员提供了一种纯 Java 方法来配置与 XML 配置概念相似的 Spring 容器。从技术角度来讲，只使用 JavaConfig 配置类来配置容器是可行的，但实际上很多人认为将 JavaConfig 与 XML 混合匹配是理想的。 

类型安全和重构友好。JavaConfig 提供了一种类型安全的方法来配置 Spring 容器。由于

Java     5.0 对泛型的支持，现在可以按类型而不是按名称检索 bean，不需要任何强制转换或基于字符串的查找。 

# 如何重新加载 Spring Boot 上的更改，而无需重新启动服务器？ 

 这可以使用 DEV 工具来实现。通过这种依赖关系，您可以节省任何更改，嵌入式 tomcat将重新启动。Spring Boot 有一个开发工具（DevTools）模块，它有助于提高开发人员的生产力。Java 开发人员面临的一个主要挑战是将文件更改自动部署到服务器并自动重启服务器。开发人员可以重新加载 Spring Boot 上的更改，而无需重新启动服务器。这将消除每次手动部署更改的需要。Spring Boot 在发布它的第一个版本时没有这个功能。这是开发人员最需要的功能。DevTools 模块完全满足开发人员的需求。该模块将在生产环境中被禁用。

它还提供 H2 数据库控制台以更好地测试应用程序。 

 

org.springframework.boot spring-boot-devtools true 

# Spring Boot 中的监视器是什么？ 

 

Spring boot actuator 是 spring 启动框架中的重要功能之一。Spring boot 监视器可帮助您访问生产环境中正在运行的应用程序的当前状态。有几个指标必须在生产环境中进行检查和监控。即使一些外部应用程序可能正在使用这些服务来向相关人员触发警报消息。监视器模块公开了一组可直接作为 HTTP URL 访问的 REST 端点来检查状态。 

 

# 如何在 Spring Boot 中禁用 Actuator 端点安全性？ 

 

默认情况下，所有敏感的 HTTP 端点都是安全的，只有具有 ACTUATOR 角色的用户才能访问它们。安全性是使用标准的 HttpServletRequest.isUserInRole 方法实施的。 我们可以使用 

management.security.enabled = false 

来禁用安全性。只有在执行机构端点在防火墙后访问时，才建议禁用安全性。 

# 如何在自定义端口上运行 Spring Boot 应用程序？ 



为了在自定义端口上运行 Spring     Boot 应用程序，您可以在 application.properties 中指定端口。 

server.port = 8090 

# 什么是 YAML？ 

YAML 是一种人类可读的数据序列化语言。它通常用于配置文件。 

与属性文件相比，如果我们想要在配置文件中添加复杂的属性，YAML 文件就更加结构化，而且更少混淆。可以看出 YAML 具有分层配置数据。 

# 如何实现 Spring Boot 应用程序的安全性？ 

 

为了实现 Spring Boot 的安全性，我们使用 spring-boot-starter-security 依赖项，并且必须添加安全配置。它只需要很少的代码。配置类将必须扩展 WebSecurityConfigurerAdapter 并覆盖其方法。 

# 如何集成 Spring Boot 和 ActiveMQ？ 

对于集成 Spring Boot 和 ActiveMQ，我们使用 

spring-boot-starter-activemq 依赖关系。 它只需要很少的配置，并且不需要样板代码。 

# 如何使用 Spring Boot 实现分页和排序？ 

 

使用 Spring Boot 实现分页非常简单。使用 Spring Data-JPA 可以实现将可分页的 

org.springframework.data.domain.Pageable 传递给存储库方法。 

# 什么是 Swagger？你用 Spring Boot 实现了它吗？ 

 

Swagger 广泛用于可视化 API，使用 Swagger UI 为前端开发人员提供在线沙箱。Swagger 是用于生成 RESTful     Web 服务的可视化表示的工具，规范和完整框架实现。它使文档能够以与服务器相同的速度更新。当通过 Swagger 正确定义时，消费者可以使用最少量的实现逻辑来理解远程服务并与其进行交互。因此，Swagger 消除了调用服务时的猜测。 

# 什么是 Spring Profiles？ 

 

Spring   Profiles 允许用户根据配置文件（dev，test，prod 等）来注册 bean。因此，当应用程序在开发中运行时，只有某些 bean 可以加载，而在 PRODUCTION 中，某些其他 bean 可以加载。假设我们的要求是 Swagger 文档仅适用于 QA 环境，并且禁用所有其他文档。这可以使用配置文件来完成。Spring Boot 使得使用配置文件非常简单。 

# 什么是 Spring Batch？ 

 

Spring Boot Batch 提供可重用的函数，这些函数在处理大量记录时非常重要，包括日志/跟踪，事务管理，作业处理统计信息，作业重新启动，跳过和资源管理。它还提供了更先进的技术服务和功能，通过优化和分区技术，可以实现极高批量和高性能批处理作业。简单以及复杂的大批量批处理作业可以高度可扩展的方式利用框架处理重要大量的信息。 

# 什么是 FreeMarker 模板？ 

 

FreeMarker 是一个基于 Java 的模板引擎，最初专注于使用 MVC 软件架构进行动态网页生成。使用 Freemarker 的主要优点是表示层和业务层的完全分离。程序员可以处理应用程序代码，而设计人员可以处理 html 页面设计。最后使用 freemarker 可以将这些结合起来，给出最终的输出页面。 

# 如何使用 Spring Boot 实现异常处理？ 

 

Spring 提供了一种使用 ControllerAdvice 处理异常的非常有用的方法。 我们通过实现一个ControlerAdvice 类，来处理控制器类抛出的所有异常。 

# 您使用了哪些 starter maven 依赖项？ 

 

使用了下面的一些依赖项 

 

spring-boot-starter-activemq spring-boot-starter-security

spring-boot-starter-web 

这有助于增加更少的依赖关系，并减少版本的冲突。 

# 什么是 CSRF 攻击？ 

 

CSRF 代表跨站请求伪造。这是一种攻击，迫使最终用户在当前通过身份验证的 Web 应用程序上执行不需要的操作。CSRF 攻击专门针对状态改变请求，而不是数据窃取，因为攻击者无法查看对伪造请求的响应。 

# 什么是 WebSockets？ 

 

WebSocket 是一种计算机通信协议，通过单个 TCP 连接提供全双工通信信道。 

WebSocket 是双向的 -使用 WebSocket 客户端或服务器可以发起消息发送。 

WebSocket 是全双工的 -客户端和服务器通信是相互独立的。 

单个 TCP 连接 -初始连接使用 HTTP，然后将此连接升级到基于套接字的连接。然后这个单一连接用于所有未来的通信 

Light -与 http 相比，WebSocket 消息数据交换要轻得多。 

# 什么是 AOP？ 

 

在软件开发过程中，跨越应用程序多个点的功能称为交叉问题。这些交叉问题与应用程序的主要业务逻辑不同。因此，将这些横切关注与业务逻辑分开是面向方面编程（AOP）的地方。 

# 什么是 Apache Kafka？ 

 

Apache Kafka 是一个分布式发布 - 订阅消息系统。它是一个可扩展的，容错的发布 - 订阅消息系统，它使我们能够构建分布式应用程序。这是一个 Apache 顶级项目。Kafka 适合离线和在线消息消费。 

# 我们如何监视所有 Spring Boot 微服务？ 

 

Spring Boot 提供监视器端点以监控各个微服务的度量。这些端点对于获取有关应用程序的信息（如它们是否已启动）以及它们的组件（如数据库等）是否正常运行很有帮助。但是，使用监视器的一个主要缺点或困难是，我们必须单独打开应用程序的知识点以了解其状态或健康状况。想象一下涉及 50 个应用程序的微服务，管理员将不得不击中所有 50 个应用程序的执行终端。

# Spring Boot 中如何实现定时任务 ?

定时任务也是一个常见的需求，Spring Boot 中对于定时任务的支持主要还是来自 Spring 框架。

在 Spring Boot 中使用定时任务主要有两种不同的方式，一个就是使用 Spring 中的 @Scheduled 注解，另一个则是使用第三方框架 Quartz。

使用 Spring 中的 @Scheduled 的方式主要通过 @Scheduled 注解来实现。

使用 Quartz ，则按照 Quartz 的方式，定义 Job 和 Trigger 即可。

# 为什么需要学习Spring Cloud

不论是商业应用还是用户应用，在业务初期都很简单，我们通常会把它实现为单体结构的应用。但是，随着业务逐渐发展，产品思想会变得越来越复杂，单体结构的应用也会越来越复杂。这就会给应用带来如下的几个问题：

 代码结构混乱：业务复杂，导致代码量很大，管理会越来越困难。同时，这也会给业务的快速迭代带来巨大挑战；

 开发效率变低：开发人员同时开发一套代码，很难避免代码冲突。开发过程会伴随着不断解决冲突的过程，这会严重的影响开发效率；

 排查解决问题成本高：线上业务发现 bug，修复 bug 的过程可能很简单。但是，由于只有一套代码，需要重新编译、打包、上线，成本很高。

由于单体结构的应用随着系统复杂度的增高，会暴露出各种各样的问题。近些年来，微服务架构逐渐取代了单体架构，且这种趋势将会越来越流行。Spring 

Cloud是目前最常用的微服务开发框架，已经在企业级开发中大量的应用。

# 什么是Spring Cloud

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、智能路由、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

# 设计目标与优缺点

设计目标

协调各个微服务，简化分布式系统开发。

## 优缺点

微服务的框架那么多比如：dubbo、Kubernetes，为什么就要使用Spring 

Cloud的呢？

优点：

-  产出于Spring大家族，Spring在企业级开发框架中无人能敌，来头很大，可以保证后续的更新、完善
-  组件丰富，功能齐全。Spring Cloud 为微服务架构提供了非常完整的支持。例如、配置管理、服务发现、断路器、微服务网关等；
-  Spring Cloud 社区活跃度很高，教程很丰富，遇到问题很容易找到解决方案服务拆分粒度更细，耦合度比较低，有利于资源重复利用，有利于提高开发效率可以更精准的制定优化服务方案，提高系统的可维护性减轻团队的成本，可以并行开发，不用关注其他人怎么开发，先关注自己的开发微服务可以是跨平台的，可以用任何一种语言开发适于互联网时代，产品迭代周期更短

缺点：

- 微服务过多，治理成本高，不利于维护系统
- 分布式系统开发的成本高（容错，分布式事务等）对团队挑战大总的来说优点大过于缺点，目前看来Spring Cloud是一套非常完善的分布式框架，目前很多企业开始用微服务、Spring Cloud的优势是显而易见的。因此对于想研究微服务架构的同学来说，学习Spring Cloud是一个不错的选择。

# Spring Cloud发展前景

Spring Cloud对于中小型互联网公司来说是一种福音，因为这类公司往往没有实力或者没有足够的资金投入去开发自己的分布式系统基础设施，使用Spring 

Cloud一站式解决方案能在从容应对业务发展的同时大大减少开发成本。同时，随着近几年微服务架构和Docker容器概念的火爆，也会让Spring Cloud在未来越来越“云”化的软件开发风格中立有一席之地，尤其是在五花八门的分布式解决方案中提供了标准化的、全站式的技术方案，意义可能会堪比当年Servlet规范的诞生，有效推进服务端软件系统技术水平的进步。

# 整体架构

![整体架构](images\整体架构.jpg)

# 主要项目

Spring Cloud的子项目，大致可分成两类，一类是对现有成熟框架"Spring 

Boot化"的封装和抽象，也是数量最多的项目；第二类是开发了一部分分布式系统的基础设施的实现，如Spring Cloud Stream扮演的就是kafka, ActiveMQ这样的角色。

## Spring Cloud Config

集中配置管理工具，分布式系统中统一的外部配置管理，默认使用Git来存储配置，可以支持客户端配置的刷新及加密、解密操作。

## Spring Cloud Netflix

Netflix OSS 开源组件集成，包括Eureka、Hystrix、Ribbon、Feign、Zuul等核心组件。

Eureka：服务治理组件，包括服务端的注册中心和客户端的服务发现机制；

Ribbon：负载均衡的服务调用组件，具有多种负载均衡调用策略；

Hystrix：服务容错组件，实现了断路器模式，为依赖服务的出错和延迟提供了容错能力；

Feign：基于Ribbon和Hystrix的声明式服务调用组件；

Zuul：API网关组件，对请求提供路由及过滤功能。

## Spring Cloud Bus

用于传播集群状态变化的消息总线，使用轻量级消息代理链接分布式系统中的节点，可以用来动态刷新集群中的服务配置。

Spring Cloud Consul

基于Hashicorp Consul的服务治理组件。

Spring Cloud Security

安全工具包，对Zuul代理中的负载均衡OAuth2客户端及登录认证进行支持。

## Spring Cloud Sleuth

Spring Cloud应用程序的分布式请求链路跟踪，支持使用Zipkin、HTrace和基于日志（例如ELK）的跟踪。

## Spring Cloud Stream

轻量级事件驱动微服务框架，可以使用简单的声明式模型来发送及接收消息，主要实现为Apache Kafka及RabbitMQ。

## Spring Cloud Task

用于快速构建短暂、有限数据处理任务的微服务框架，用于向应用中添加功能性和非功能性的特性。

Spring Cloud Zookeeper

基于Apache Zookeeper的服务治理组件。

Spring Cloud Gateway

API网关组件，对请求提供路由及过滤功能。

## Spring Cloud OpenFeign

基于Ribbon和Hystrix的声明式服务调用组件，可以动态创建基于Spring MVC 注解的接口实现用于服务调用，在Spring Cloud 2.0中已经取代Feign成为了一等公民。

# Spring Cloud的版本关系

Spring Cloud是一个由许多子项目组成的综合项目，各子项目有不同的发布节奏。 为了管理Spring Cloud与各子项目的版本依赖关系，发布了一个清单，其中包括了某个Spring Cloud版本对应的子项目版本。 为了避免Spring Cloud版本号与子项目版本号混淆，Spring Cloud版本采用了名称而非版本号的命名，

这些版本的名字采用了伦敦地铁站的名字，根据字母表的顺序来对应版本时间顺序，例如Angel是第一个版本，Brixton是第二个版本。 当Spring Cloud的发布

内容积累到临界点或者一个重大BUG被解决后，会发布一个"service 

releases"版本，简称SRX版本，比如Greenwich.SR2就是Spring Cloud发布的 Greenwich版本的第2个SRX版本。目前Spring Cloud的最新版本是Hoxton。

## Spring Cloud和SpringBoot版本对应关系

| Spring Cloud   Version | SpringBo  ot   Version |
| ---------------------- | ---------------------- |
| Hoxton                 | 2.2.x                  |
| Greenwic h             | 2.1.x                  |
| Finchley               | 2.0.x                  |
| Edgware                | 1.5.x                  |
| Dalston                | 1.5.x                  |

## Spring Cloud和各子项目版本对应关系

| More Actions  Compon  ent   | Edgware.  SR6     | Greenwic  h.SR2 |
| --------------------------- | ----------------- | --------------- |
| spring- cloud- bus          | 1.3.4.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud-  commons     | 1.3.6.RELE    ASE | 2.1.2.RELE  ASE |
| spring- cloud- config       | 1.4.7.RELE  ASE   | 2.1.3.RELE  ASE |
| spring-  cloud-     netflix | 1.4.7.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- security     | 1.2.4.RELE  ASE   | 2.1.3.RELE  ASE |
| spring- cloud- consul       | 1.3.6.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- sleuth       | 1.3.6.RELE  ASE   | 2.1.1.RELE  ASE |
| spring- cloud- stream       | Ditmars.S  R5     | Fishtown.  SR3  |
| spring- cloud- zookeepe r   | 1.2.3.RELE  ASE   | 2.1.2.RELE  ASE |
| spring-  boot               | 1.5.21.REL  EASE  | 2.1.5.RELE  ASE |
| spring- cloud- task         | 1.2.4.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- gateway      | 1.0.3.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- openfeig n   | 暂无              | 2.1.2.RELE  ASE |

注意：Hoxton版本是基于SpringBoot 2.2.x版本构建的，不适用于1.5.x版本。随着2019年8月SpringBoot 1.5.x版本停止维护，Edgware版本也将停止维护。

# SpringBoot和SpringCloud的区别？

SpringBoot专注于快速方便的开发单个个体微服务。

SpringCloud是关注全局的微服务协调整理治理框架，它将SpringBoot开发的一个个单体微服务整合并管理起来，为各个微服务之间提供，配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等集成服务

SpringBoot可以离开SpringCloud独立使用开发项目， 但是SpringCloud离不开SpringBoot ，属于依赖的关系

SpringBoot专注于快速、方便的开发单个微服务个体，SpringCloud关注全局的服务治理框架。

## 使用 Spring Boot 开发分布式微服务时，我们面临以下问题

（1）  与分布式系统相关的复杂性-这种开销包括网络问题，延迟开销，带宽问题，安全问题。

（2）  服务发现-服务发现工具管理群集中的流程和服务如何查找和互相交谈。它涉及一个服务目录，在该目录中注册服务，然后能够查找并连接到该目录中的服务。

（3）  冗余-分布式系统中的冗余问题。

（4）  负载平衡 --负载平衡改善跨多个计算资源的工作负荷，诸如计算机，计算机集群，网络链路，中央处理单元，或磁盘驱动器的分布。

（5）  性能-问题 由于各种运营开销导致的性能问题。

（6）部署复杂性-Devops 技能的要求。

# 服务注册和发现是什么意思？Spring Cloud 如何实现？

当我们开始一个项目时，我们通常在属性文件中进行所有的配置。随着越来越多的服务开发和部署，添加和修改这些属性变得更加复杂。有些服务可能会下降，而某些位置可能会发生变化。手动更改属性可能会产生问题。 Eureka 服务注册和发现可以在这种情况下提供帮助。由于所有服务都在 Eureka 服务器上注册并通过调用 Eureka 服务器完成查找，因此无需处理服务地点的任何更改和处理。

Spring Cloud 和dubbo区别?

（1）服务调用方式 dubbo是RPC springcloud Rest Api （2）注册中心,dubbo 是zookeeper springcloud是eureka，也可以是 zookeeper

（3）服务网关,dubbo本身没有实现，只能通过其他第三方技术整合， springcloud有Zuul路由网关，作为路由服务器，进行消费者的请求分

发,springcloud支持断路器，与git完美集成配置文件支持版本控制，事物总线实现配置文件的更新与服务自动装配等等一系列的微服务架构要素。

# 负载平衡的意义什么？

在计算中，负载平衡可以改善跨计算机，计算机集群，网络链接，中央处理单元或磁盘驱动器等多种计算资源的工作负载分布。负载平衡旨在优化资源使用，最大化吞吐量，最小化响应时间并避免任何单一资源的过载。使用多个组件进行负载平衡而不是单个组件可能会通过冗余来提高可靠性和可用性。负载平衡通常涉及专用软件或硬件，例如多层交换机或域名系统服务器进程。

# 什么是 Hystrix？它如何实现容错？

Hystrix 是一个延迟和容错库，旨在隔离远程系统，服务和第三方库的访问点，当出现故障是不可避免的故障时，停止级联故障并在复杂的分布式系统中实现弹性。

通常对于使用微服务架构开发的系统，涉及到许多微服务。这些微服务彼此协作。

思考以下微服务

![Hystrix](images\Hystrix.jpg)

假设如果上图中的微服务 9 失败了，那么使用传统方法我们将传播一个异常。

但这仍然会导致整个系统崩溃。

随着微服务数量的增加，这个问题变得更加复杂。微服务的数量可以高达 1000.

这是 hystrix 出现的地方 我们将使用 Hystrix 在这种情况下的 Fallback 方法功

能。我们有两个服务 employee-consumer 使用由 employee-consumer 公开的服务。

简化图如下所示

![简化](images\简化.jpg)

现在假设由于某种原因，employee-producer 公开的服务会抛出异常。我们在这种情况下使用 Hystrix 定义了一个回退方法。这种后备方法应该具有与公开服

务相同的返回类型。如果暴露服务中出现异常，则回退方法将返回一些值。

# 什么是 Hystrix 断路器？我们需要它吗？

由于某些原因，employee-consumer 公开服务会引发异常。在这种情况下使用Hystrix 我们定义了一个回退方法。如果在公开服务中发生异常，则回退方法返回一些默认值。

![Hystrix断路器](images\Hystrix断路器.jpg)

如果 firstPage method() 中的异常继续发生，则 Hystrix 电路将中断，并且员

工使用者将一起跳过 firtsPage 方法，并直接调用回退方法。 断路器的目的是给第一页方法或第一页方法可能调用的其他方法留出时间，并导致异常恢复。可能发生的情况是，在负载较小的情况下，导致异常的问题有更好的恢复机会 。

## 什么是 Netflix Feign？它的优点是什么？

Feign 是受到 Retrofit，JAXRS-2.0 和 WebSocket 启发的 java 客户端联编程序。

Feign 的第一个目标是将约束分母的复杂性统一到 http apis，而不考虑其稳定性。

在 employee-consumer 的例子中，我们使用了 employee-producer 使用 REST模板公开的 REST 服务。

但是我们必须编写大量代码才能执行以下步骤（1）使用功能区进行负载平衡。

（2）  获取服务实例，然后获取基本 URL。

（3）  利用 REST 模板来使用服务。 前面的代码如下

```java

    @Controller
    public class ConsumerControllerClient {
        @Autowired
        private LoadBalancerClient loadBalancer;
        public void getEmployee() throws RestClientException, IOException {

            ServiceInstance serviceInstance=loadBalancer.choose("employee-producer");

            System.out.println(serviceInstance.getUri());

            String baseUrl=serviceInstance.getUri().toString();

            baseUrl=baseUrl+"/employee";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response=null;
            try{
                response=restTemplate.exchange(baseUrl,
                        HttpMethod.GET, getHeaders(),String.class);
            }catch (Exception ex)
            {
                System.out.println(ex);
            }
            System.out.println(response.getBody());
        }
```

之前的代码，有像 NullPointer 这样的例外的机会，并不是最优的。我们将看到如何使用 Netflix Feign 使呼叫变得更加轻松和清洁。如果 Netflix Ribbon 依赖关系也在类路径中，那么 Feign 默认也会负责负载平衡。

## 什么是 Spring Cloud Bus？我们需要它吗？

考虑以下情况：我们有多个应用程序使用 Spring Cloud Config 读取属性，而

Spring Cloud Config 从 GIT 读取这些属性。

下面的例子中多个员工生产者模块从 Employee Config Module 获取 Eureka 注册的财产。

![获取Eureka注册财产](images\获取Eureka注册财产.jpg)

如果假设 GIT 中的 Eureka 注册属性更改(img)为指向另一台 Eureka 服务器，会发生什么情况。在这种情况下，我们将不得不重新启动服务以获取更新的属性。还有另一种使用执行器端点/刷新的方式。但是我们将不得不为每个模块单独调

用这个 url。例如，如果 Employee Producer1 部署在端口 8080 上，则调用 http：// localhost：8080 / refresh。同样对于 Employee Producer2 

http：//localhost：8081 / refresh 等等。这又很麻烦。这就是 Spring Cloud Bus 发挥作用的地方。

![Spring_Bus](images\Spring_Bus.jpg)

Spring Cloud Bus 提供了跨多个实例刷新配置的功能。因此，在上面的示例中，如果我们刷新 Employee Producer1，则会自动刷新所有其他必需的模

块。如果我们有多个微服务启动并运行，这特别有用。这是通过将所有微服务连接到单个消息代理来实现的。无论何时刷新实例，此事件都会订阅到侦听此代理的所有微服务，并且它们也会刷新。可以通过使用端点/总线/刷新来实现对任何单个实例的刷新。

# Spring Cloud断路器的作用

当一个服务调用另一个服务由于网络原因或自身原因出现问题，调用者就会等待被调用者的响应 当更多的服务请求到这些资源导致更多的请求等待，发生连锁效应（雪崩效应）断路器有完全打开状态:一段时间内 达到一定的次数无法调用 并且多次监测没有恢复的迹象 断路器完全打开 那么下次请求就不会请求到该服务半开:短时间内 有恢复迹象 断路器会将部分请求发给该服务，正常调用时 断路器关闭

关闭：当服务一直处于正常状态 能正常调用

## 什么是Spring Cloud Config?

在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是 config server，二是config client。

使用：

（1）  添加pom依赖

（2）  配置文件添加相关配置

（3）  启动类添加注解@EnableConfigServer

## 什么是Spring Cloud Gateway?

Spring Cloud Gateway是Spring Cloud官方推出的第二代网关框架，取代Zuul 网关。网关作为流量的，在微服务系统中有着非常作用，网关常见的功能有路由转发、权限校验、限流控制等作用。

使用了一个RouteLocatorBuilder的bean去创建路由，除了创建路由

RouteLocatorBuilder可以让你添加各种predicates和filters，predicates断言的意思，顾名思义就是根据具体的请求的规则，由具体的route去处理，filters 是各种过滤器，用来对请求做各种判断和修改。