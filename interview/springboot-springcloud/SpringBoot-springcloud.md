

# Spring Boot

## 什么是 Spring Boot？ 

多年来，随着新功能的增加，spring 变得越来越复杂。只需访问 https://spring.io/projects

页面，我们就会看到可以在我们的应用程序中使用的所有 Spring 项目的不同功能。如果必须启动一个新的 Spring 项目，我们必须添加构建路径或添加 Maven 依赖关系，配置应用程序服务器，添加 spring 配置。因此，开始一个新的 spring 项目需要很多努力，因为我们现在必须从头开始做所有事情。 

Spring   Boot 是解决这个问题的方法。Spring     Boot 已经建立在现有 spring 框架之上。使用 spring 启动，我们避免了之前我们必须做的所有样板代码和配置。因此，Spring  Boot 可以帮助我们以最少的工作量，更加健壮地使用现有的 Spring 功能。 

## Spring Boot 有哪些优点？ 

Spring Boot 的优点有： 

减少开发，测试时间和努力。 

使用 JavaConfig 有助于避免使用 XML。 

避免大量的 Maven 导入和各种版本冲突。 

提供意见发展方法。 

通过提供默认值快速开始开发。 

没有单独的 Web 服务器需要。这意味着你不再需要启动 Tomcat，Glassfish 或其他任何东西。 

需要更少的配置 因为没有 web.xml 文件。只需添加用@ Configuration 注释的类，然后添加用@Bean 注释的方法，Spring 将自动加载对象并像以前一样对其进行管理。您甚至可以将@Autowired 添加到 bean 方法中，以使 Spring 自动装入需要的依赖关系中。 

基于环境的配置 使用这些属性，您可以将您正在使用的环境传递到应用程序：Dspring.profiles.active = {enviornment}。在加载主应用程序属性文件后，Spring 将在（application{environment} .properties）中加载后续的应用程序属性文件。 

## 什么是 JavaConfig？ 

Spring JavaConfig 是 Spring 社区的产品，它提供了配置 Spring IoC 容器的纯 Java 方法。因此它有助于避免使用 XML 配置。使用 JavaConfig 的优点在于： 

面向对象的配置。由于配置被定义为 JavaConfig 中的类，因此用户可以充分利用 Java 中的面向对象功能。一个配置类可以继承另一个，重写它的@Bean 方法等。 

减少或消除 XML 配置。基于依赖注入原则的外化配置的好处已被证明。但是，许多开发人员不希望在 XML 和 Java 之间来回切换。JavaConfig 为开发人员提供了一种纯 Java 方法来配置与 XML 配置概念相似的 Spring 容器。从技术角度来讲，只使用 JavaConfig 配置类来配置容器是可行的，但实际上很多人认为将 JavaConfig 与 XML 混合匹配是理想的。 

类型安全和重构友好。JavaConfig 提供了一种类型安全的方法来配置 Spring 容器。由于Java     5.0 对泛型的支持，现在可以按类型而不是按名称检索 bean，不需要任何强制转换或基于字符串的查找。 

## 如何重新加载 Spring Boot 上的更改，而无需重新启动服务器？ 

 这可以使用 DEV 工具来实现。通过这种依赖关系，您可以节省任何更改，嵌入式 tomcat将重新启动。Spring Boot 有一个开发工具（DevTools）模块，它有助于提高开发人员的生产力。Java 开发人员面临的一个主要挑战是将文件更改自动部署到服务器并自动重启服务器。开发人员可以重新加载 Spring Boot 上的更改，而无需重新启动服务器。这将消除每次手动部署更改的需要。Spring Boot 在发布它的第一个版本时没有这个功能。这是开发人员最需要的功能。DevTools 模块完全满足开发人员的需求。该模块将在生产环境中被禁用。

它还提供 H2 数据库控制台以更好地测试应用程序。 

org.springframework.boot spring-boot-devtools true 

## Spring Boot 中的监视器是什么？ 

Spring boot actuator 是 spring 启动框架中的重要功能之一。Spring boot 监视器可帮助您访问生产环境中正在运行的应用程序的当前状态。有几个指标必须在生产环境中进行检查和监控。即使一些外部应用程序可能正在使用这些服务来向相关人员触发警报消息。监视器模块公开了一组可直接作为 HTTP URL 访问的 REST 端点来检查状态。 

## 如何在 Spring Boot 中禁用 Actuator 端点安全性？ 

默认情况下，所有敏感的 HTTP 端点都是安全的，只有具有 ACTUATOR 角色的用户才能访问它们。安全性是使用标准的 HttpServletRequest.isUserInRole 方法实施的。 我们可以使用 management.security.enabled = false 来禁用安全性。只有在执行机构端点在防火墙后访问时，才建议禁用安全性。 

## 如何在自定义端口上运行 Spring Boot 应用程序？ 

为了在自定义端口上运行 Spring     Boot 应用程序，您可以在 application.properties 中指定端口。 

server.port = 8090 

## 什么是 YAML？ 

YAML 是一种人类可读的数据序列化语言。它通常用于配置文件。 

与属性文件相比，如果我们想要在配置文件中添加复杂的属性，YAML 文件就更加结构化，而且更少混淆。可以看出 YAML 具有分层配置数据。 

## 如何实现 Spring Boot 应用程序的安全性？ 

为了实现 Spring Boot 的安全性，我们使用 spring-boot-starter-security 依赖项，并且必须添加安全配置。它只需要很少的代码。配置类将必须扩展 WebSecurityConfigurerAdapter 并覆盖其方法。 

## 如何集成 Spring Boot 和 ActiveMQ？ 

对于集成 Spring Boot 和 ActiveMQ，我们使用 spring-boot-starter-activemq 依赖关系。 它只需要很少的配置，并且不需要样板代码。 

## 如何使用 Spring Boot 实现分页和排序？ 

 使用 Spring Boot 实现分页非常简单。使用 Spring Data-JPA 可以实现将可分页的 org.springframework.data.domain.Pageable 传递给存储库方法。 

## 什么是 Swagger？你用 Spring Boot 实现了它吗？ 

Swagger 广泛用于可视化 API，使用 Swagger UI 为前端开发人员提供在线沙箱。Swagger 是用于生成 RESTful     Web 服务的可视化表示的工具，规范和完整框架实现。它使文档能够以与服务器相同的速度更新。当通过 Swagger 正确定义时，消费者可以使用最少量的实现逻辑来理解远程服务并与其进行交互。因此，Swagger 消除了调用服务时的猜测。 

## 什么是 Spring Profiles？ 

Spring   Profiles 允许用户根据配置文件（dev，test，prod 等）来注册 bean。因此，当应用程序在开发中运行时，只有某些 bean 可以加载，而在 PRODUCTION 中，某些其他 bean 可以加载。假设我们的要求是 Swagger 文档仅适用于 QA 环境，并且禁用所有其他文档。这可以使用配置文件来完成。Spring Boot 使得使用配置文件非常简单。 

## 什么是 Spring Batch？ 

Spring Boot Batch 提供可重用的函数，这些函数在处理大量记录时非常重要，包括日志/跟踪，事务管理，作业处理统计信息，作业重新启动，跳过和资源管理。它还提供了更先进的技术服务和功能，通过优化和分区技术，可以实现极高批量和高性能批处理作业。简单以及复杂的大批量批处理作业可以高度可扩展的方式利用框架处理重要大量的信息。 

## 什么是 FreeMarker 模板？ 

FreeMarker 是一个基于 Java 的模板引擎，最初专注于使用 MVC 软件架构进行动态网页生成。使用 Freemarker 的主要优点是表示层和业务层的完全分离。程序员可以处理应用程序代码，而设计人员可以处理 html 页面设计。最后使用 freemarker 可以将这些结合起来，给出最终的输出页面。 

## 如何使用 Spring Boot 实现异常处理？ 

Spring 提供了一种使用 ControllerAdvice 处理异常的非常有用的方法。 我们通过实现一个ControlerAdvice 类，来处理控制器类抛出的所有异常。 

## 您使用了哪些 starter maven 依赖项？ 

使用了下面的一些依赖项 

spring-boot-starter-activemq spring-boot-starter-security

spring-boot-starter-web 

这有助于增加更少的依赖关系，并减少版本的冲突。 

## 什么是 CSRF 攻击？ 

CSRF 代表跨站请求伪造。这是一种攻击，迫使最终用户在当前通过身份验证的 Web 应用程序上执行不需要的操作。CSRF 攻击专门针对状态改变请求，而不是数据窃取，因为攻击者无法查看对伪造请求的响应。 

## 什么是 WebSockets？ 

WebSocket 是一种计算机通信协议，通过单个 TCP 连接提供全双工通信信道。 

WebSocket 是双向的 -使用 WebSocket 客户端或服务器可以发起消息发送。 

WebSocket 是全双工的 -客户端和服务器通信是相互独立的。 

单个 TCP 连接 -初始连接使用 HTTP，然后将此连接升级到基于套接字的连接。然后这个单一连接用于所有未来的通信 Light -与 http 相比，WebSocket 消息数据交换要轻得多。 

## 什么是 AOP？ 

在软件开发过程中，跨越应用程序多个点的功能称为交叉问题。这些交叉问题与应用程序的主要业务逻辑不同。因此，将这些横切关注与业务逻辑分开是面向方面编程（AOP）的地方。 

## 什么是 Apache Kafka？ 

Apache Kafka 是一个分布式发布 - 订阅消息系统。它是一个可扩展的，容错的发布 - 订阅消息系统，它使我们能够构建分布式应用程序。这是一个 Apache 顶级项目。Kafka 适合离线和在线消息消费。 

## 我们如何监视所有 Spring Boot 微服务？ 

Spring Boot 提供监视器端点以监控各个微服务的度量。这些端点对于获取有关应用程序的信息（如它们是否已启动）以及它们的组件（如数据库等）是否正常运行很有帮助。但是，使用监视器的一个主要缺点或困难是，我们必须单独打开应用程序的知识点以了解其状态或健康状况。想象一下涉及 50 个应用程序的微服务，管理员将不得不击中所有 50 个应用程序的执行终端。

## Spring Boot 中如何实现定时任务 ?

定时任务也是一个常见的需求，Spring Boot 中对于定时任务的支持主要还是来自 Spring 框架。

在 Spring Boot 中使用定时任务主要有两种不同的方式，一个就是使用 Spring 中的 @Scheduled 注解，另一个则是使用第三方框架 Quartz。

使用 Spring 中的 @Scheduled 的方式主要通过 @Scheduled 注解来实现。

使用 Quartz ，则按照 Quartz 的方式，定义 Job 和 Trigger 即可。

# Spring Cloud

## 为什么需要学习Spring Cloud

不论是商业应用还是用户应用，在业务初期都很简单，我们通常会把它实现为单体结构的应用。但是，随着业务逐渐发展，产品思想会变得越来越复杂，单体结构的应用也会越来越复杂。这就会给应用带来如下的几个问题：

 代码结构混乱：业务复杂，导致代码量很大，管理会越来越困难。同时，这也会给业务的快速迭代带来巨大挑战；

 开发效率变低：开发人员同时开发一套代码，很难避免代码冲突。开发过程会伴随着不断解决冲突的过程，这会严重的影响开发效率；

 排查解决问题成本高：线上业务发现 bug，修复 bug 的过程可能很简单。但是，由于只有一套代码，需要重新编译、打包、上线，成本很高。

由于单体结构的应用随着系统复杂度的增高，会暴露出各种各样的问题。近些年来，微服务架构逐渐取代了单体架构，且这种趋势将会越来越流行。Spring Cloud是目前最常用的微服务开发框架，已经在企业级开发中大量的应用。

## 什么是Spring Cloud

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、智能路由、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

## Spring Cloud 设计目标与优缺点

设计目标

协调各个微服务，简化分布式系统开发。

### 优缺点

微服务的框架那么多比如：dubbo、Kubernetes，为什么就要使用Spring Cloud的呢？

优点：

-  产出于Spring大家族，Spring在企业级开发框架中无人能敌，来头很大，可以保证后续的更新、完善
-  组件丰富，功能齐全。Spring Cloud 为微服务架构提供了非常完整的支持。例如、配置管理、服务发现、断路器、微服务网关等；
-  Spring Cloud 社区活跃度很高，教程很丰富，遇到问题很容易找到解决方案服务拆分粒度更细，耦合度比较低，有利于资源重复利用，有利于提高开发效率可以更精准的制定优化服务方案，提高系统的可维护性减轻团队的成本，可以并行开发，不用关注其他人怎么开发，先关注自己的开发微服务可以是跨平台的，可以用任何一种语言开发适于互联网时代，产品迭代周期更短

缺点：

- 微服务过多，治理成本高，不利于维护系统
- 分布式系统开发的成本高（容错，分布式事务等）对团队挑战大总的来说优点大过于缺点，目前看来Spring Cloud是一套非常完善的分布式框架，目前很多企业开始用微服务、Spring Cloud的优势是显而易见的。因此对于想研究微服务架构的同学来说，学习Spring Cloud是一个不错的选择。

## Spring Cloud发展前景

Spring Cloud对于中小型互联网公司来说是一种福音，因为这类公司往往没有实力或者没有足够的资金投入去开发自己的分布式系统基础设施，使用Spring Cloud一站式解决方案能在从容应对业务发展的同时大大减少开发成本。同时，随着近几年微服务架构和Docker容器概念的火爆，也会让Spring Cloud在未来越来越“云”化的软件开发风格中立有一席之地，尤其是在五花八门的分布式解决方案中提供了标准化的、全站式的技术方案，意义可能会堪比当年Servlet规范的诞生，有效推进服务端软件系统技术水平的进步。

## Spring Cloud 整体架构

![整体架构](images\整体架构.jpg)

## Spring Cloud 主要项目

Spring Cloud的子项目，大致可分成两类，一类是对现有成熟框架"Spring Boot化"的封装和抽象，也是数量最多的项目；第二类是开发了一部分分布式系统的基础设施的实现，如Spring Cloud Stream扮演的就是kafka, ActiveMQ这样的角色。

### Spring Cloud Config

集中配置管理工具，分布式系统中统一的外部配置管理，默认使用Git来存储配置，可以支持客户端配置的刷新及加密、解密操作。

### Spring Cloud Netflix

Netflix OSS 开源组件集成，包括Eureka、Hystrix、Ribbon、Feign、Zuul等核心组件。

Eureka：服务治理组件，包括服务端的注册中心和客户端的服务发现机制；

Ribbon：负载均衡的服务调用组件，具有多种负载均衡调用策略；

Hystrix：服务容错组件，实现了断路器模式，为依赖服务的出错和延迟提供了容错能力；

Feign：基于Ribbon和Hystrix的声明式服务调用组件；

Zuul：API网关组件，对请求提供路由及过滤功能。

### Spring Cloud Bus

用于传播集群状态变化的消息总线，使用轻量级消息代理链接分布式系统中的节点，可以用来动态刷新集群中的服务配置。

Spring Cloud Consul：基于Hashicorp Consul的服务治理组件。

Spring Cloud Security：安全工具包，对Zuul代理中的负载均衡OAuth2客户端及登录认证进行支持。

### Spring Cloud Sleuth

Spring Cloud应用程序的分布式请求链路跟踪，支持使用Zipkin、HTrace和基于日志（例如ELK）的跟踪。

### Spring Cloud Stream

轻量级事件驱动微服务框架，可以使用简单的声明式模型来发送及接收消息，主要实现为Apache Kafka及RabbitMQ。

### Spring Cloud Task

用于快速构建短暂、有限数据处理任务的微服务框架，用于向应用中添加功能性和非功能性的特性。

Spring Cloud Zookeeper：基于Apache Zookeeper的服务治理组件。

Spring Cloud Gateway：API网关组件，对请求提供路由及过滤功能。

### Spring Cloud OpenFeign

基于Ribbon和Hystrix的声明式服务调用组件，可以动态创建基于Spring MVC 注解的接口实现用于服务调用，在Spring Cloud 2.0中已经取代Feign成为了一等公民。

## Spring Cloud的版本关系

Spring Cloud是一个由许多子项目组成的综合项目，各子项目有不同的发布节奏。 为了管理Spring Cloud与各子项目的版本依赖关系，发布了一个清单，其中包括了某个Spring Cloud版本对应的子项目版本。 为了避免Spring Cloud版本号与子项目版本号混淆，Spring Cloud版本采用了名称而非版本号的命名，

这些版本的名字采用了伦敦地铁站的名字，根据字母表的顺序来对应版本时间顺序，例如Angel是第一个版本，Brixton是第二个版本。 当Spring Cloud的发布

内容积累到临界点或者一个重大BUG被解决后，会发布一个"service releases"版本，简称SRX版本，比如Greenwich.SR2就是Spring Cloud发布的 Greenwich版本的第2个SRX版本。目前Spring Cloud的最新版本是Hoxton。

### Spring Cloud和SpringBoot版本对应关系

| Spring Cloud   Version | SpringBo  ot   Version |
| ---------------------- | ---------------------- |
| Hoxton                 | 2.2.x                  |
| Greenwic h             | 2.1.x                  |
| Finchley               | 2.0.x                  |
| Edgware                | 1.5.x                  |
| Dalston                | 1.5.x                  |

### Spring Cloud和各子项目版本对应关系

| More Actions  Compon  ent | Edgware.  SR6     | Greenwic  h.SR2 |
| ------------------------- | ----------------- | --------------- |
| spring- cloud- bus        | 1.3.4.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- commons    | 1.3.6.RELE    ASE | 2.1.2.RELE  ASE |
| spring- cloud- config     | 1.4.7.RELE  ASE   | 2.1.3.RELE  ASE |
| spring-  cloud-netflix    | 1.4.7.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- security   | 1.2.4.RELE  ASE   | 2.1.3.RELE  ASE |
| spring- cloud- consul     | 1.3.6.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- sleuth     | 1.3.6.RELE  ASE   | 2.1.1.RELE  ASE |
| spring- cloud- stream     | Ditmars.S  R5     | Fishtown.  SR3  |
| spring- cloud- zookeeper  | 1.2.3.RELE  ASE   | 2.1.2.RELE  ASE |
| spring-  boot             | 1.5.21.REL  EASE  | 2.1.5.RELE  ASE |
| spring- cloud- task       | 1.2.4.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- gateway    | 1.0.3.RELE  ASE   | 2.1.2.RELE  ASE |
| spring- cloud- openfeign  | 暂无              | 2.1.2.RELE  ASE |

注意：Hoxton版本是基于SpringBoot 2.2.x版本构建的，不适用于1.5.x版本。随着2019年8月SpringBoot 1.5.x版本停止维护，Edgware版本也将停止维护。

## SpringBoot和SpringCloud的区别？

SpringBoot专注于快速方便的开发单个个体微服务。

SpringCloud是关注全局的微服务协调整理治理框架，它将SpringBoot开发的一个个单体微服务整合并管理起来，为各个微服务之间提供，配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等集成服务

SpringBoot可以离开SpringCloud独立使用开发项目， 但是SpringCloud离不开SpringBoot ，属于依赖的关系

SpringBoot专注于快速、方便的开发单个微服务个体，SpringCloud关注全局的服务治理框架。

### 使用 Spring Boot 开发分布式微服务时，我们面临以下问题

（1）  与分布式系统相关的复杂性-这种开销包括网络问题，延迟开销，带宽问题，安全问题。

（2）  服务发现-服务发现工具管理群集中的流程和服务如何查找和互相交谈。它涉及一个服务目录，在该目录中注册服务，然后能够查找并连接到该目录中的服务。

（3）  冗余-分布式系统中的冗余问题。

（4）  负载平衡 --负载平衡改善跨多个计算资源的工作负荷，诸如计算机，计算机集群，网络链路，中央处理单元，或磁盘驱动器的分布。

（5）  性能-问题 由于各种运营开销导致的性能问题。

（6）部署复杂性-Devops 技能的要求。

# eureka

## 服务注册和发现是什么意思？Spring Cloud 如何实现？

当我们开始一个项目时，我们通常在属性文件中进行所有的配置。随着越来越多的服务开发和部署，添加和修改这些属性变得更加复杂。有些服务可能会下降，而某些位置可能会发生变化。手动更改属性可能会产生问题。 Eureka 服务注册和发现可以在这种情况下提供帮助。由于所有服务都在 Eureka 服务器上注册并通过调用 Eureka 服务器完成查找，因此无需处理服务地点的任何更改和处理。

Spring Cloud 和dubbo区别?

（1）服务调用方式 dubbo是RPC springcloud Rest Api 

（2）注册中心,dubbo 是zookeeper springcloud是eureka，也可以是 zookeeper

（3）服务网关,dubbo本身没有实现，只能通过其他第三方技术整合， springcloud有Zuul路由网关，作为路由服务器，进行消费者的请求分发,springcloud支持断路器，与git完美集成配置文件支持版本控制，事物总线实现配置文件的更新与服务自动装配等等一系列的微服务架构要素。

## 负载平衡的意义什么？

在计算中，负载平衡可以改善跨计算机，计算机集群，网络链接，中央处理单元或磁盘驱动器等多种计算资源的工作负载分布。负载平衡旨在优化资源使用，最大化吞吐量，最小化响应时间并避免任何单一资源的过载。使用多个组件进行负载平衡而不是单个组件可能会通过冗余来提高可靠性和可用性。负载平衡通常涉及专用软件或硬件，例如多层交换机或域名系统服务器进程。

## 什么是 Hystrix？它如何实现容错？

Hystrix 是一个延迟和容错库，旨在隔离远程系统，服务和第三方库的访问点，当出现故障是不可避免的故障时，停止级联故障并在复杂的分布式系统中实现弹性。

通常对于使用微服务架构开发的系统，涉及到许多微服务。这些微服务彼此协作。

思考以下微服务

![Hystrix](images\Hystrix.jpg)

假设如果上图中的微服务 9 失败了，那么使用传统方法我们将传播一个异常。

但这仍然会导致整个系统崩溃。

随着微服务数量的增加，这个问题变得更加复杂。微服务的数量可以高达 1000.

这是 hystrix 出现的地方 我们将使用 Hystrix 在这种情况下的 Fallback 方法功能。我们有两个服务 employee-consumer 使用由 employee-consumer 公开的服务。

简化图如下所示

![简化](images\简化.jpg)

现在假设由于某种原因，employee-producer 公开的服务会抛出异常。我们在这种情况下使用 Hystrix 定义了一个回退方法。这种后备方法应该具有与公开服务相同的返回类型。如果暴露服务中出现异常，则回退方法将返回一些值。

## 什么是 Hystrix 断路器？我们需要它吗？

由于某些原因，employee-consumer 公开服务会引发异常。在这种情况下使用Hystrix 我们定义了一个回退方法。如果在公开服务中发生异常，则回退方法返回一些默认值。

![Hystrix断路器](images\Hystrix断路器.jpg)

如果 firstPage method() 中的异常继续发生，则 Hystrix 电路将中断，并且员工使用者将一起跳过 firtsPage 方法，并直接调用回退方法。 断路器的目的是给第一页方法或第一页方法可能调用的其他方法留出时间，并导致异常恢复。可能发生的情况是，在负载较小的情况下，导致异常的问题有更好的恢复机会 。

## 什么是 Netflix Feign？它的优点是什么？

Feign 是受到 Retrofit，JAXRS-2.0 和 WebSocket 启发的 java 客户端联编程序。

Feign 的第一个目标是将约束分母的复杂性统一到 http apis，而不考虑其稳定性。

在 employee-consumer 的例子中，我们使用了 employee-producer 使用 REST模板公开的 REST 服务。

但是我们必须编写大量代码才能执行以下步骤

（1）使用功能区进行负载平衡。

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

考虑以下情况：我们有多个应用程序使用 Spring Cloud Config 读取属性，而Spring Cloud Config 从 GIT 读取这些属性。

下面的例子中多个员工生产者模块从 Employee Config Module 获取 Eureka 注册的财产。

![获取Eureka注册财产](images\获取Eureka注册财产.jpg)

如果假设 GIT 中的 Eureka 注册属性更改(img)为指向另一台 Eureka 服务器，会发生什么情况。在这种情况下，我们将不得不重新启动服务以获取更新的属性。还有另一种使用执行器端点/刷新的方式。但是我们将不得不为每个模块单独调用这个 url。例如，如果 Employee Producer1 部署在端口 8080 上，则调用 http：// localhost：8080 / refresh。同样对于 Employee Producer2 http：//localhost：8081 / refresh 等等。这又很麻烦。这就是 Spring Cloud Bus 发挥作用的地方。

![Spring_Bus](images\Spring_Bus.jpg)

Spring Cloud Bus 提供了跨多个实例刷新配置的功能。因此，在上面的示例中，如果我们刷新 Employee Producer1，则会自动刷新所有其他必需的模块。如果我们有多个微服务启动并运行，这特别有用。这是通过将所有微服务连接到单个消息代理来实现的。无论何时刷新实例，此事件都会订阅到侦听此代理的所有微服务，并且它们也会刷新。可以通过使用端点/总线/刷新来实现对任何单个实例的刷新。

# Spring Cloud断路器的作用

当一个服务调用另一个服务由于网络原因或自身原因出现问题，调用者就会等待被调用者的响应, 当更多的服务请求到这些资源导致更多的请求等待，发生连锁效应（雪崩效应）

断路器有完全打开状态:一段时间内 达到一定的次数无法调用 并且多次监测没有恢复的迹象 断路器完全打开 那么下次请求就不会请求到该服务

半开:短时间内 有恢复迹象 断路器会将部分请求发给该服务，正常调用时断路器关闭

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

## Hystrix服务降级底层是如何实现的？

 Hystrix 实现服务降级的功能是通过重写 HystrixCommand 中的 getFallback() 方法，当Hystrix的run方法或construct执行发生错误时转而执行getFallback() 方法。

##  你曾阅读过 Spring Cloud 的源码吗？

我们知道，Spring Cloud 是通过 Spring Boot 集成了很多第三方框架构成的。现在准备解析 Spring Cloud 中某子框架的源码，若还没有找到合适的入手位置，那么从哪里开始解析可能是一个不错的选择。请谈一下你的认识。

我自己曾阅读过 Spring Cloud 中的 Eureka、OpenFeign、Ribbon 等的源码。对于一个未曾阅读过的子框架源码，我认为从自动配置类开始解析可能是一个不错的选择。

我们知道 Spring Cloud 是通过 Spring Boot 将其它第三方框架集成进来的。Spring Boot 最大的特点就是自动配置，我们可以通过导入相关 Starter 来实现需求功能的自动配置、相关核心业务类实例的创建等。也就是说，核心业务类都是集中在自动配置类中的。所以从这里下手分析应该是个不错的选择。

那么从哪里可以找到这个自动配置类呢？

从导入的 starter 依赖工程的 META-INF 目录中的 spring.factory 文件中可以找到。该文件的内容为 key-value 对，查找 EnableAutoConfiguration 的全限定性类名作为 key 的 value，这个 value 就是我们要找到的自动配置类。

## @EnableConfigurationProperties 注解对于 Starter 的定义很重要，请谈一上你对这个注解的认识。

@EnableConfigurationProperties 注解在 Starter 定义时主要用于读取 application.yml 配置文件中相关的属性，并封装到指定类型的实例中，以备 Starter 中的核心业务实例使用。具体来说，它就是开启了对@ConfigurationProperties 注解的 Bean 的自动注册，注解到Spring 容器中。这种 Bean 有两种注册方式：在配置类使用@Bean 方法注册，或直接使用该注解的 value 属性进行注册。若在配置类中使用@Bean 注册，则需要在配置类中定义一个@Bean 方法，该方法的返回值为“使用@ConfigurationProperties 注解标注”的类。若直接使用该注解的 value 属性进行注册，则需要将这个“使用@ConfigurationProperties 注解标注” 的类作为 value 属性值出现即可。

## Spring Boot 中定义了很多条件注解，这些注解一般用于对配置类的控制。在这些条件注解中有一个@ConditionalOnMissingBean 注解，你了解过嘛？请谈一下你对它的认识。

@ConditionalOnMissingBean 注解是 Spring Boot 提供的众多条件注册中的一个。其表示的意义是，当容器中没有指定名称或指定类型的 Bean 时，该条件为 true。不过，这里需要强调一点的是，这里要查找的“容器”是可以指定的。通过 search 属性指定。其 search 的范围有三种：仅搜索当前配置类容器；搜索所有层次的父类容器，但不包含当前配置类容器；搜索当前配置类容器及其所有层次的父类容器，这个是默认搜索范围。

## Spring Cloud 中默认情况下对于 Eureka Client 实例的创建中，@RefreshScope 注解是比较重要的，请谈一下你对这个注解的认识。

@RefreshScope 注解是 Spring Cloud 中定义的一个注解。该注解用于配置类，可以添加在配置类上，也可以添加在@Bean 方法上。其表示的意思是，该@Bean 方法会以多例的形式生成会自动刷新的Bean 实例。这种方式就等价于在 Spring 的 xml 配置文件中指定<bean/> 标签的 scope 属性值为 refresh。当然，若一个配置类上添加了该注解，则表示该配置类中的所有@Bean 方法创建的实例都是@RefreshScope 的。

## Spring Cloud 中默认情况下对于 Eureka Client 实例的创建是在 EurekaClient 的自动配置类中通过@Bean 方法完成的。但在源码中，这个@Bean 方法上同时出现了@RefreshScope、@ConditionalOnMissionBean，与@Lazy 注解，从这些注解的意义来分析，是否存在矛盾呢？ 它们联合使用又是什么意思呢？请谈一下你的看法。

首先来说，这三个注解的意义都是比较复杂的。

@RefreshScope 注解是 Spring Cloud 中定义的一个注解。其表示的意思是，该@Bean 方法会以多例的形式生成会自动刷新的 Bean 实例。

@ConditionalOnMissionBean 注解表示的意思是，只有当容器中没有@Bean 要创建的实例时才会创建新的实例，即这里创建的@Bean 实例是单例的。

@Lazy 注解表示延迟实例化。即在当前配置类被实例化时并不会调用这里的@Bean 方法去创建实例，而是在代码执行过程中，真正需要这个@Bean 方法的实例时才会创建。

这三个注解的联用不存在矛盾，其要表达的意思是，这个@Bean 会以延迟实例化的形式创建一个单例的对象，而该对象具有自动刷新功能。

## Spring Cloud 中大量地使用了条件注解，其中@ConditionalOnRefreshScope 注解对于Eureka Client 的创建非常重要。请谈一下你对这个注解的认识。

首先，关于条件注解，实际是 Spring Boot 中出现的内容，其一般应用于配置类中。表示只有当该条件满足时才会创建该实例。而您提到的@ConditionalOnRefreshScope 注解， 其实际是 Eureka Client 的自动配置类中的一个内部注解。该注解不同于 Spring Boot 中的一般性注解的是，其是一个复合条件注解，其复合的条件有三个：

l 在当前类路径下具有 RefreshScope 类

l 在容器中要具有 RefreshAutoConfiguration 类的实例

l 指定的 eureka.client.refresh.enable 属性值为 true。不过，其缺省值就是 true。这也就是为什么我们的配置文件默认支持自动更新的原因。

只有当这个复合注解中的三个条件均成立时，@ConditionalOnRefreshScope 注解才满足条件。此时才有可能会调用创建 Eureka Client 的@Bean 方法。所以，该注解对于 Eureka Client 的创建非常重要。

## 你刚才已经谈过了对@ConditionalOnRefreshScope 注解的认识，非常不错。不过， 与这个注解相对应的另一个注解@ConditionalOnMissingRefreshScope，你是否了解？若关注过，谈一下你的认识。（不将面试者问死誓不罢 ）

 @ConditionalOnMissingRefreshScope 我也曾了解过。这个注解就像@ConditionalOnRefreshScope 注解一样，也是一个复合条件注解，其也包含了三个条件。不同的是，这个注解中的条件是或的关系，只要满足其中一条这个注解就匹配上了。而@ConditionalOnRefreshScope 注解中的三个条件是与的关系，必须所有条件均满足其才能匹配上。

这个或的关系是通过让一个复合条件类继承自一个能够表示或关系的复合条件父类

AnyNestedCondition 实现的。这样的话，这个复合条件类中定义的多个内部条件类中，只要有一个匹配上，那么这个复合条件类就算匹配上了。

## Spring Cloud 中 Eureka Client 的源码中有一个非常重要的类 Applications，其被称为客户端注册表。请谈一下你对它的认识。

Applications 类实例中封装了来自于 Eureka Server 的所有注册信息，通常称其为“客户端注册表”。只所以要强调“客户端”是因为，服务端的注册表不是这样表示的，是一个Map。

该类中封装着一个非常重要的 Map 集合，key 为微服务名称，而 Value 则为 Application实例。Application 类中封装了一个 Set 集合，集合元素为“可以提供该微服务的所有主机的InstanceInfo”。也就是说，Applications 中封装着所有微服务的所有提供者信息。

##  Eureka 源码中 InstanceInfo 类中具有两个最终修改时间戳，这两个时间戳对于 Eureka 的 Server 端与 Client 端源码的理解都比较重要。这两个时间戳你了解过吗？若了解过，请谈一下你对它们的认识。

InstanceInfo 实例中封装着一个 Eureka Client 的所有信息，其就可以代表了一个 Eureka Client。其封装的两个最终修改时间戳分别为 lastDirtyTimestamp 与 lastUpdatedTimestamp。这两个时间戳的区别是：

l lastDirtyTimestamp：记录 intance 在 Client 被修改的时间。该修改会被传递到 Server 端。

l lastUpdatedTimestamp：记录 intance 状态在 Server 端被修改的时间。

## Eureka 源码中 InstanceInfo 类中具有两个状态属性，是哪两个，你了解过它们吗？

Eureka 源码中 InstanceInfo 类具有两个状态属性，分别是 status 与 overriddenStatus。下面我依次谈一下我对它们的了解。

status 就是当前 Client 的工作状态。只有在 Server 端注册表中该 Client 的状态为 UP 时， 其才会被其它服务发现，才可对外提供提供服务。

overriddenStatus 是在 Client 提交注册请求与 renew 续约请求时用于计算当前 Client 在Server 端的 Status 状态的。

## Spring Cloud 中 Eureka Client 与 Eureka Server 的通信，及 Eureka Server 间的通信是如何实现的？请简单介绍一下。

Spring Cloud 中 Eureka Client 与 Eureka Server 的通信，及 Eureka Server 间的通信，均采用的是 Jersey 框架。

Jersey 框架是一个开源的 RESTful 框架，实现了 JAX-RS 规范。该框架的作用与 SpringMVC 是相同的，其也是用户提交 URI 后，在处理器中进行路由匹配，路由到指定的后台业务。这个路由功能同样也是通过处理器完成的，只不过这里的处理器不叫Controller，而叫Resource。

## Spring Cloud 中 Eureka Client 在启动时需要从 Eureka Server 中下载注册表到本地进行缓存，以备进行负载均衡调用。请谈一下你对这个启动时下载注册表过程的认识。

Spring Cloud 中 Eureka Client 在启动时需要从 Eureka Server 中下载注册表到本地进行缓存。这次下载属于全量下载，即要将 Server 端所有注册信息 Applicaitons 全部下载到本地并缓存。当然，若指定可以从远程 Region 获取，其也会通过其所连接的这个 Server，将远程 Region 中的注册信息也全部获取到。这个过程称为获取客户端注册表。

获取“客户端注册表”最终执行的操作是，通过 Jersey 框架提交了一个 GET 请求，然后获取到的 Applications 实例结果。

若获取失败，其会从本地备用注册表中获取并缓存。

## Spring Cloud 中 Eureka Client 需要定时从 Eureka Server 中获取注册表信息，这个过程称为服务发现。请谈一下你对这个获取过程的认识。

Eureka Client 从 Eureka Server 中获取注册表分为两种情况，一种是将 Server 中所有注册信息全部下载到当前客户端本地并进行缓存，这种称为全量获取；一种是仅获取在 Server 中发生变更的注册信息到本地，然后根据变更修改本地缓存中的注册信息，这种称为增量获取。当 Client 在启动时第一次下载就属于全量获取，而后期每 30 秒从 Server 下载一次的定时下载属于增量下载。无论是哪种情况，Client 都是通过 Jersey 框架向 Server 发送了一个GET 请求。只不过是，不同的获取方式，提交请求时携带的参数是不同的。

## Spring Cloud 中 Eureka Client 需要注册到 Eureka Server 中，请谈一下你对这个注册过程的认识。

Eureka Client 向Eureka Server 提交的注册请求，实际是通过Jersey 框架完成的一次POST提交，将当前 Client 的封装对象 intanceInfo 提交到 Server 端，写入到 Server 端的注册表中。但这个注册请求在默认情况下并不是在Client 启动时直接提交的，而是在Client 向Server发送续约信息时，由于其未在 Server 中注册，所以 Server 会中其返回 404，在这种情况下， 而引发的 Client 注册。当然，若 Client 的续约信息发生了变更 Client 也会提交注册请求。

## Spring Cloud 中 Eureka Client 需要定时从 Eureka Server 中更新注册信息。对于这个定时器及其执行过程，请谈一下你的看法。

Spring Cloud 中 Eureka Client 需要定时从 Eureka Server 中更新注册信息。但这里是使用了 one-shot action 的一次性定时器实现的 repeated 定时执行。这个 repeated 过程是通过其一次性的定时任务实现的：当这个一次性定时任务执行完毕后，会调用启动下一次的定时任务。

## Spring Cloud 中 Eureka Client 需要定时从 Eureka Server 中更新注册信息。这个定时任务是通过一个 one-shot action 的定时器完成的。其为什么不直接使用一个 repeated 的定时器呢？请谈一下你的看法。

Spring Cloud 中 Eureka Client 需要定时从 Eureka Server 中更新注册信息。这个定时任务是通过一个 one-shot action 的定时器完成的。只所以选择 one-shot action 的定时器来完成一个 repeated 的事情，其主要目的就是方便控制 delay，保证任务顺利完成。

定时器要执行的任务是通过网络从 Server 下载注册信息。而我们知道，网络传输存在不稳定性，不同的传输数据可能走的网络链路是不同的，而不同的链路的传输时间可能也是不同的。本次传输超时，下次重试可能走的链路不同就不超时了。

repeated 定时器的执行原理是，本次任务的开始，必须在上一次的任务完成后，不存在超时。即只要没完成就不会通过执行下次任务进行重试。

使用 one-shot action 定时器完成 repeated 定时任务时，若本次定时任务出现了超时， 则可以在下次任务执行之前增大定时器的 delay。当然，若下载速率都很快，也可将已经增大的 delay 再进行减小。方便控制 delay，保证任务的顺利完成。

## Futue 是 JDK5 中提供的一个接口。其方法 cancel()是一个经常被用到的方法，请谈一下你对这个方法的认识。

Futue 是 JDK5 中提供的一个 JUC 的接口，其用于执行一些异步任务。对于其执行的异步任务，可以通过 cancel()方法尝试着进行取消。其用法为：

l 若任务已经完成或已经取消，则本次取消失败。

l 若任务在启动之前就调用了 cancel()，则这个任务将不会再执行。

l 若任务已经启动，此时再执行 cancel()，那么这个执行的任务是否立即被中断，取决于cancel()方法的参数。若参数为 true，则会立即中断任务的执行；若参数为 false，则会让正在执行中的任务执行完毕，然后再中断。

l 若cancel()方法执行完毕后，顺序执行isDone()或isCancelled()方法，这些方法均返回true。

## Futue 是 JDK5 中提供的一个接口。其方法 get()是一个经常被用到的方法，请谈一下你对这个方法的认识。

Futue 是 JDK5 中提供的一个 JUC 的接口，其用于执行一些异步任务，并通过 get()方法可以获得该异步任务的结果。get()方法是一个阻塞的方法，可以为其指定阻塞的最长时长。

l 若在指定时长内异步操作完成，则阻塞会被立即唤醒；

l 若在指定时长内该异步操作被 cancel()，则阻塞也会被立即唤醒，并抛出一个CancellationException；

l 若在指定时长内未发生任务事情，则阻塞也会被唤醒，并抛出一个 TimeoutException；

l 若 get()方法没有指定阻塞时长，则其会一直阻塞下去，直到任务完成或取消。

## Spring Cloud 中 Eureka Client 向 Eureka Server 发送增量获取注册表请求，Server 会返回给 Client 一个 delta，关于这个返回值 delta，请谈一下你的看法。

Spring Cloud 中 Eureka Client 向 Eureka Server 发送增量获取注册表请求，Server 会返回给 Client 一个 delta，这个 delta 是一个 Applications 类型的变量。

l Server 返回的这个 delta 值若为 null，则表示 Server 端基于安全考虑，禁止增量下载， 其会自动进行全量下载。

l Server 返回的这个 delta 值不为 null，但其包含的 application 数量为 0，则表示没有更新内容。若数量大于 0，则表示有更新内容。有更新内容，则需要将更新添加到本地缓存的注册表 applications 中。

## Spring Cloud 中 Eureka Client 向 Eureka Server 发送增量获取注册表请求，Server 端是如何知道哪些 intance 对这个 Client 来说是变更过的？请谈一下你的看法。

当 Eureka Server 接收到 Eureka Client 发送的增量获取注册表请求后，其并不知道哪些instance 对于这个 Client 来说是更新过的。但是在 Server 中维护着一个“最近变更队列”，无论对于哪个 Client 的增量请求，Server 端都是将该队列中的 instance 变更信息发送给了

Client。当然，Server 中有一个定时任务，当这个 instance 的变更信息不再属于“最近”时， 会将该 instance 变更信息从队列中清除。

当 Client 接收到 Server 发送的增量变更信息后，Client 端有一种判断机制，可以判断出其接收到这个增量信息对它自己来说是否出现了更新丢失。即出现了队列中清除掉的变更信息，并没有更新到当前 Client 本地。若发生更新丢失，Client 会再发起全量获取，以保证 Client 获取到的注册表是最完整的注册表。

## Spring Cloud 中Eureka Client 从Eureka Server 中增量获取到的注册信息，都是在Server端发生了变更的 instanceInfo 信息。这些变更信息中包含变更的类型。其中对于 ADDED 与MODIFIED 这两种类型的变更，我们发现处理方式是相同的，为什么？请谈一下你的看法。

Eureka Client 接收到的来自 Server 的增量注册信息后，对于添加变更与修改变更的处理方式的确是相同的，都是采用了添加变更的处理方式。其实，无论是添加的 InstanceInfo 还是修改的 InstanceInfo，Client 首先都是根据该 InstanceInfo 的 id，从本地 InstanceInfo 的 Set 集合中将其删除，然后再将新来的变更的 InstanceInfo 加入 Set 集合。只不过，对于添加变更，其在原来的 Set 集合中找不到其要删除的 InstanceInfo 而已。

只所以能够被根据 InstanceId 在 Set 集合中进行删除，是因为 Set 集合的元素 InstanceInfo重写了 equals()方法——根据 instanceId 进行元素相等判断。

## Spring Cloud 中 Eureka Client 从 Eureka Server 中获取注册信息，有全量获取与增量获取之分。为什么全量获取到 Client 本地后没有做 Region 的分离，而增量下载后却将这些发生变更的信息分为了本地 Region 与远程 Region？

Spring Cloud 中 Eureka Client 从 Eureka Server 中获取注册信息，有全量获取与增量获取之分。

全量获取一般是在应用启动时第一次获取注册表数据时发起的。这次获取的目的就是为了让应用能够马上进行服务。所以 Server 把返回的注册信息并没有区分 Region，但这些注册信息包含了所有本地 Region 与远程 Region 中的注册信息。

增量获取一般是从应用第二次获取注册表数据时发起的。这次获取的目的不仅是为了能够获取到距离自己最近的微服务信息，而且是为了保证服务的可用性（Eureka 是 AP 的）。若当前 Region 中无需要的服务时，可以从远程 Region 获取。所以 Server 返回的注册信息对Rgion 进行了区分，而 Client 端也对本地 Region 与远程 Region 中的注册信息进行了分别缓存。

##  Spring Cloud 中 Eureka Client 会向 Eureka Server 进行定时续约，请谈一下你对这个定时续约的认识。

Eureka Client 会向 Eureka Server 进行定时续约，即会进行定时心跳。其最终就是通过Jersey 框架向 Eureka Server 提交一个 PUT 请求。该 PUT 请求没有携带任何请求体，而 Eureka Server 仅仅就是接收到一个 PUT 请求。但通过请求，Server 能够知道这个请求的发送者instanceId。而 Eureka Server 就是通过这个发送者 instanceId 从注册表中查找其 instance 信息的。当然，Server 也给 Client 发送来了响应信息：若从 Server 的注册表中找到了该续约的instance，则返回该 instanceInfo 实例；若没有找到，则返回 404。

## Spring Cloud 中 Eureka Client 会定时检测 Client 的 instanceInfo 是否发生了变更。请谈一下你对这个定时任务的认识。

Eureka Client 会定时检测 Client 的 instanceInfo 是否发生了变更，其主要是检测了两样内容：一个是检测数据中心中的关于当前 instanceInfo 的信息是否变更，一个是检测配置文件中当前 instanceInfo 中的续约信息是否变更。只要发生了变更，则将变化后的信息发送给Server，这个发送执行的是 register()注册。

## Spring Cloud 中 Eureka Client 向 Eureka Server 提交 registrer()注册请求的时机较多， 请简单总结一下。

Eureka Client 向 Eureka Server 提交的 register()注册请求的情况有三种：

l 在 Client 实例初始化时直接提交 register()注册请求

l 定时发送心跳时，服务端返回 404，此时 Client 会发出 registrer()注册请求

l 定时更新 Client 续约信息给 Server 时，只要 Client 续约信息发生变更，其提交的就是register()注册请求

##  Spring Cloud 中 Eureka Client 在做定时更新续约信息给 Server 时有一个定时任务， 定时查看本地配置文件中的 instanceInfo 是否发生了变更。这个定时任务在 Eureka Client 启动时通过 start()启动了定时任务，该定时器是一个 one-shot action 定时器，其会调用InstanceInfoReplicator 的 run()方法。而该方法会再次启动一个 one-shot action 的定时任务， 实现了 repeated 定时执行。然而，当 instanceInfo 的状态发生变更后会调用一个按需更新方法 onDemandUpdate()，该方法同样会调用 InstanceInfoReplicator 的 run()方法，再次启动一个 one-shot action 的定时任务，实现了 repeated 定时执行。这样的话，只要发生一次状态变更，就会启动一个 repeated 的定时任务持续执行下去。那么若 InstanceInfo 的状态多次发生变更，是否就会启动很多的一直持续执行的该定时任务了？请谈一下你的看法。

答案当然是否定的。关键就在于两点：一个是，前面题目中无论是 start()方法还是InstanceInfoReplicator 的 run()方法，在启动了定时任务后，都会将定时任务实例 future 写入到一个原子引用类型的缓存中，且后放入的会将先放入的覆盖，即这个缓存中存放的始终为最后一个定时任务。第二个关键点是这个 onDemandUpdate()方法。其在调用InstanceInfoReplicator 的 run()方法之前首先将这个缓存中的异步操作 cancel()，即将最后一个定时任务结束，然后才会再启动一个新的定时任务。所以，只会同时存在一个该定时任务。

## Spring Cloud 中 Eureka Client 的续约配置信息默认情况下是允许动态变更的。为了限制变更的频率，Eureka Client 使用了一种限流策略，是什么策略？请谈一下你对这种策略的认识。

Spring Cloud 中 Eureka Client 的续约配置信息默认情况下是允许动态变更的。为了限制变更的频率，Eureka Client 使用了令牌桶算法。

该算法实现中维护着一个队列，首先所有元素需要进入到队列中，当队列满时，未进入到队列的元素将丢弃。进入到队列中的元素是否可以被处理，需要看其是否能够从令牌桶中拿到令牌。一个元素从令牌桶中拿到一个令牌，那么令牌桶中的令牌数量就会减一。若元素生成速率较快，则其从令牌桶中获取到令牌的速率就会较大。一旦令牌桶中没有了令牌，则队列很快就会变满，那么再来的元素将被丢弃。

## Spring Cloud 中 Eureka Client 的哪些操作会引发客户端 InstanceInfo 的最新修改时间戳 lastDirtyTimestamp 的变化？请谈一下你的认识。

Spring Cloud 中 Eureka Client 的操作中有两处操作可以引发客户端 InstanceInfo 的最新修改时间戳 lastDirtyTimestamp 的变化。

l 在进行第一次心跳发送时，由于 Server 中没有发现该 InstanceInfo 而向其返回了 404。此时的 Client 会修改 lastDirtyTimestamp 的值。

l 在续约信息发生更新时修改 lastDirtyTimestamp 的值。

## Spring Cloud 中 Eureka Client 通过 Actuator 提交的 POST 请求的 shutdown 进行服务下架时，其调用的下架处理方法我们从哪里找到它？请谈一下你的思路。

Spring Cloud 中 Eureka Client 通过 Actuator 提交的 POST 请求的 shutdown 进行服务下架，就是要销毁 Eureka Client 实例。而该 Eureka Client 实例是在 EurekaClient 的自动配置类中通过@Bean 方法创建的。所以，这个下架处理方法应该是@Bean 注解的 destroyMethod 属性指定的方法。

##  Spring Cloud 中 Eureka Client 通过 Actuator 提交的 POST 请求的 shutdown 进行服务下架时，其 Client 内部都做了些什么重要工作？请谈一下你对这个服务下架的认识。

Eureka Client 通过 Actuator 提交的 POST 请求的 shutdown 进行服务下架时，其内部主要完成了四样工作：

l 将状态变更监听器删除

l 停止了一堆定时任务的执行，并关闭了定时器

l 通过 Jersey 框架向 Eureka Server 提交了一个 DELETE 请求

l 关闭了一些其它相关工作

## 对于 Spring Cloud 中的 Eureka，用户通过平滑上下线方式进行 Client 状态的修改。这个状态修改请求是被客户端的哪个类处理的，这个类实例是在何时创建的？请谈一下你的认识。

对于 Spring Cloud 中的 Eureka，用户通过平滑上下线方式进行 Client 状态的修改。这个状态修改请求是被客户端的 ServiceRegistryEndpoint 类实例的 setStatus()方法处理的。这个类实例是在 Spring Cloud 应用启动时被加载创建的。具体来说，spring-cloud-starter 依赖于spring-cloud-common，而该 common 依赖加载并实例化了 ServiceRegistryAutoConfigration 配置类。在该配置类中实例化了 ServiceRegistryEndpoint 类。

## 对于 Spring Cloud 中的 Eureka，用户通过 Actuator 的 service-registry 监控终端提交状态修改请求，请谈一下你对这个请求处理过程的认识。

对于 Spring Cloud 中的 Eureka，用户通过 Actuator 的 service-registry 监控终端提交状态修改请求，服务平滑上下线就属于这种情况，但这种情况不仅限于服务平滑上下线。

当 Client 提交的状态为 UP 或 OUT_OF_SERVICE 时，属于平滑上下线场景。该请求会被 Client 接收并直接再以PUT 请求的方式提交给Server，在Client 端并未修改Client 的任何状态。Server在接收到这个请求后，会从注册表中找到该 Client 对应的 InstanceInfo，修改其overriddenStatus、status 为指定状态。

当 Client 提交的状态为 CANCEL_OVERRIDE 时，是要将 Server 端当前 Client 对应InstanceInfo 的 overriddenStatus 从一个缓存 map 中删除，并将其 overriddenStatus 与 status 修改为 UNKNOWN 状态。这个缓存 map 中记录着注册到当前 Server 中的每一个 instanceInfo 对应的 overriddenStatus，这个 map 中的状态值对于其它 Client 发现一个 InstanceInfo 的对外表现状态 status 非常重要。当服务端的某 InstanceInfo 的 overriddenStatus 变为 UNKNOWN 时，该 Client 发送的心跳 Server 是不接收的。Server 会向该 Client 返回 404。

## 对于 Spring Cloud 中的 Eureka，用户通过 Actuator 的 service-registry 监控终端提交状态修改请求，如果用户提交的状态是一个非法状态会怎么样？请谈一下你的认识。

对于 Spring Cloud 中的 Eureka，用户通过 Actuator 的 service-registry 监控终端提交状态修改请求，这个状态值一般为五个标准状态 UP、DOWN、OUT_OF_SERVICE、STARTING、UNKNOWN 中的 UP 或 OUT_OF_SERVICE，也可以是 CANCEL_OVERRIDE。但若用户提交的不是这六种情况之一，系统会将其最终归结为 UNKNOWN 状态。

## Spring Cloud 中 EurekaServerAutoConfiguration 自动配置类被实例化的一个前提条件是，容器中要有一个 Marker 实例，这个 Marker 实例是在哪被添加到了容器？

Spring Cloud 中关于 Eureka Server 的这个 Marker 实例，就是 Eureka Server 的一个标识，一个开关。该实例被添加到了容器，Eureka Server 就开启了。其是在 Eureke Server 启动类上的@EnableEurekaServer 注解中被实例化并添加到的容器。所以，若没有添加该注解， Eureka Server 启动类的启动是不会创建启动 Eureka Server 的。

## Spring Cloud 中 Eureka Client 会注册到 Eureka Server 的注册表中，在 Server 中这个注册表是以怎样的形式存在的？当Eureka Client 将Eureke Server 中的注册信息下载到本地后， 这个注册表又是以怎样的形式存在的？请谈一下你的认识。

Spring Cloud 的 Eureka Server 中的这个注册表是一个双层 Map，外层 map 的 key 为微服务名称，value 为内层 map。内层 map 的 key 为 instanceId，value 为 Lease 对象。Lease 对象中包含一个执有者 Holder 属性，表示该 Lease 对象所属的 InstanceInfo。

当 Eureka Client 将 Server 端的注册表下载到了本地，该注册表是以 Applications 形式出现的。Applications 中维护着一上 Map 集合，key 为微服务名称，value 为 Application 实例。该 Application 实例中包含了所有提供该微服务名称的 InstanceInfo 信息。因为 Application 中也维护着一个 Map，key 为 instanceId，value 为 InstanceInfo。

## Eureka Client 提交的状态修改请求，Eureka Server 是如何处理的，Server 端都做了哪些变更？请谈一下你的认识。

Eureka Client 提交的状态修改请求，Eureka Server 在接收到后，首先根据该 Client 的微服务名称及 instanceId 在 Server 端注册表中进行了查找。若没有找到，则直接返回 404； 若找到了，其会执行两大任务：

l 任务一：将客户端修改请求中的新状态写入到注册表中。

l 任务二：将写入到当前 server 注册表中新的状态同步到其它 server。

而在第一项任务中完成的重要工作有如下几项：

l 使用新状态替换缓存 overriddenInstanceStatusMap 中的老状态

l 修改 instanceInfo 的 overriddenStatus 状态为新状态

l 修改 instanceInfo 的 status 状态为新状态

l 更新来自于请求参数的客户端修改时间戳 lastDirtyTimestamp

l 将本次修改形为记录到 instanceInfo 的行为类型中

l 修改服务端修改时间戳 lastUpdatedTimestamp

l 将本次修改记录到最近更新队列 recentlyChanngeQueue 中

## Eureka Server 中的注册表发生了变更，其是怎样将变更同步到其它 Server 的？请谈一下你的看法。

Eureka Server 中的注册表发生了变更，并不是一定要同步给其它 Server 的，需要分情况处理。

若这个变更是由 Client 端直接引发，则当前 Server 会遍历所有其它 Server，通过 Jersey框架向每一个其它 Server 发送变更请求。这样就实现了同步。

若这个变更是由其它 Server 端发送的变更，则其仅仅会在本地变更一下即可，不会再向其它 Server 发送变更请求，以防止出现无限递归。

## Eureka Client 提交的 CANCEL_OVERRIDE 状态修改请求，Eureka Server 是如何处理的，Server 端都做了哪些变更？请谈一下你的认识。

Eureka Client 提交的 CANCEL_OVERRIDE 状态修改请求，Eureka Server 在接收到后，首先根据该 Client 的微服务名称及 instanceId 在 Server 端注册表中进行了查找。若没有找到， 则直接返回 404；若找到了，其会执行两大任务：

l 任务一：将 Server 端删除 overridden 状态。

l 任务二：将此变更同步到其它 server。

删除 overridden 状态，从哪里删除呢？这个删除处理，在 Server 端主要完成了三样工作：

l 将该 instanceInfo 在缓存集合 overriddenInstanceStatusMap 中的 Entry 删除。

l 将 instanceInfo 中的 overridden 状态变更为 UNKNOWN。

l 将 instanceInfo 中的 status 状态变更为 UNKNOWN。

overriddenInstanceStatusMap 中的缓存的 instanceInfo 的 overridden 状态，对于后续注册、续约等请求处理时instanceInfo 的状态判断起很大作用。另外此时，该Client 发送的心跳Server 是不接收的。Server 会向该 Client 返回 404。

## Eureka Client 提交的续约请求，Eureka Server 是如何处理的？请谈一下你的认识。

客户端续约请求，即客户端向服务端发送心跳。客户端的心跳提交的是一个没有携带任何请求体的 PUT 请求，不过其在请求 URI 中携带了心跳的发出者的 InstanceId，及当前心跳发出者的状态。Server 端对于续约请求，主要完成了两若项任务：

l 当 Server 接收到 Client 发送的续约信息后，根据注册表中当前 InstanceInfo 的状态信息计算出其状态，然后更新为新的 status。

l 将心跳同步到其它 server。

l 在 Server 间的同步过程可能会导致 Server 间 overridden 状态的不一致。所以又进行了该状态的统一。

## Eureka Server 间进行续约同步过程中可能会导致overridden 状态的不一致，为什么？ 请谈一下你的看法。

首先要清楚一点，Eureka 是 AP 的，是允许出现 Server 间数据不一致的。例如，当前Eureka 中由于客户端下架请求而从注册表中删除了某 Client，在进行 Server 间同步时，由于另一个 Server 处于自我保护模式，所以其是不能删除 Client 的。此时就出现了 Server 间数据的不一致。

下面再来说续约。无论是直接处理Client 的续约请求，还是处理Server 间续约同步，Server端对于续约的处理，根本不涉及 lastDirtyTimestamp 时间戳，及 overridden 状态。这一点从续约的源码中是可以看出来的。那么有可能会出现以下场景：Client 通过 Actuator 修改了状态，而这个状态修改操作在 Server 间同步时并没有同步成功，出现了 Server 间对于同一个InstanceInfo 中 overridden 状态的不一致。

虽然 Eureka 本身是 AP 的，但其仍是尽量想让 Eureka 间实现同步，所以在其发生最频繁的续约中解决了这个问题。只不过，由于续约本身根本不涉及 overridden 状态，仅靠续约是解决不了的。所以需要在 Eureka Server 的配置文件中添加专门的配置解决这个问题。不过，这个属性设置默认是开启的。

## Eureka Server 在进行续约处理时，若发现其计算的当前 isntanceInfo 的 status 状态为 UNKNOWN，说明什么？请谈一下你的看法。

Eureka Server 在进行续约处理时，若发现其计算的当前 instanceInfo 的 status 状态为UNKNOWN，说明这个计算结果是在 OverrideExistsRule 规则中计算出的结果，即当前instanceInfo 的 overridden 状态为 UNKNOWN。对于一个 InstanceInfo 来说，可以从缓存 map 中读取到其 overridden 状态为 UNKNOWN，只能有一种情况：这个 UNKNOWN 的 overridden 状态是通过 Actuator 的 CANCEL_OVERRIDE 修改的状态，即用户取消了该 InstanceInfo 的overridden 状态。

那么也就是说，Eureka Server 在进行续约处理时，若发现其计算的当前 instanceInfo 的status 状态为 UNKNOWN，则说明该 InstanceInfo 已经不对外提供服务了。

## Eureka Client 的注册请求被 Server 接收到后，会首先判断该 Client 在注册表中是否存在。按理说，这里是注册处理，注册表中应该是不存在该 Client 的 lease 信息的，但的确会出现新注册的 Client 在注册表中存在的情况，这是为什么？请谈一下你的看法。

Eureka Client 的注册请求被 Server 接收到后，注册表中可能会出现新注册的 Client 在注册表中已经存在的情况。这是因为，当 Client 的 instanceInfo 的续约信息发生了变更，Client 的“定时更新 InstanceInfo 信息给 Server”的定时任务发出的是 register()请求，但该客户端其实在之前已经注册过了。此时就会出现注册表中已经存在该 instanceId 的情况。

## Eureka Server 在处理新注册的 Client 在注册表中已经存在的情况时会出现一种比较奇怪的情况：当前新注册的 InstanceInfo 中的 lastDirtyTimestamp，比注册表中缓存的当前这个 InstanceInfo 中的 lastDirtyTimestamp 小。即后注册的反而过时，这是为什么？请谈一下你的看法。

Eureka Server 在处理新注册的 Client 在注册表中已经存在的情况时会出现，当前新注册的 InstanceInfo 中的 lastDirtyTimestamp，比注册表中缓存的当前这个 InstanceInfo 中的lastDirtyTimestamp 小的情况，即后注册的反而过时。这是因为，若 Client 的 instanceInfo 的续约信息相继发生了两次变更，Client 就提交了两次 register()请求。但是由于网络原因，第二次注册请求先到达 Server。当第一次注册请求到达后就会出现“后到达的注册请求中携带的 instanceInfo 的最后修改时间反而过时”的情况。

## Eureka Server 在处理 Client 的注册时请求时，是否可能出现新注册的 registrant 的OverriddenStatus 状态不是 UNKNOWN 的场景呢？为什么？请谈一下你的看法。

这种场景是可能会出现的。且只有一种情况：当前注册的 registrant 是由于其续约信息发生了变更而引发的注册，且在续约信息变更之前用户通过 Actuator 修改过状态。

当然，这种通过 Actuator 修改的状态仅仅修改的是 Server 端注册表中的状态，并没有修改客户端的任何状态。这个修改的结果实际是通过客户端定时更新客户端注册表时，将所有变更信息下载到的客户端，其中就包含它自己状态的修改信息。

## Eureka Server 在处理 Client 的全量下载注册信息请求时可以设置从自己的只读缓存readOnlyCacheMap 中获取到所有注册到自己的注册信息，而 readOnlyCacheMap 中的数据是定时同步的读写缓存 readWriteCacheMap 的。这样的话就存在一个问题：这个定时更新无论更新频率多么高，一定存在用户从 readOnlyCacheMap 中读取的数据与 readWriteCacheMap 中不一致的情况，为什么不直接从 readWriteCacheMap 中读取？也就是说，这样的设计好处是什么？请谈一下你的认识。

这样设计的目的是为了保证在并发环境下“集合迭代的稳定性”。集合迭代的稳定性指的是，当一个共享变量是集合且存在并发读写操作时，要保证在对共享集合进行读操作时能够读取到稳定的数据，即在读取时不能对其执行写操作，但又不能妨碍了写操作的执行。此时就可以将集合的读写功能进行分离，创建出两个共享集合：一个专门用于处理写功能， 外来的数据写入到这个集合；一个专门用于处理读功能，定时同步写集合的数据。这种方案存在的弊端是，无法保证只读集合中的数据与读写集合中数据的随时完全一致。当然，这种不一致在下一次定时同步时就会达到一致。所以这种方案的应用场景是对数据的实时性要求不是很高的情况。

## Eureka Server 在处理 Client 的全量下载注册信息请求与处理增量下载请求有什么不同？请谈一下你的认识。

Eureka Server 在处理 Client 的全量下载注册信息请求时，其读取的是当前 Server 注册表 registry 中的注册信息，而处理增量下载请求时根本就没有操作注册表 registry，而是直接读取了最近更新队列 recentlyChangeQueue 中的信息。这两种请求的处理方式，操作了两上不同的共享集合。

## Eureka Server 在处理 Client 的增量下载注册信息请求时是从 recentlyChangeQueue 最近更新队列中直接获取的数据，这个 recently 是多久？如果超过了这个 recently 时间又是如何处理的？请谈一下你的认识。

最近更新队列 recentlyChangeQueue 中的 recently 是可能通过配置文件属性指定的， 默认为 3 分钟。当 recentlyChangeQueue 中的元素超过了 3 分钟，那么系统会自动将这些过期的元素删除。只不过这个删除操作是一个 repeated 定时任务，在 AbstractInstanceRegistry 类的构造器中被创建并启动，默认每 30 秒执行一次。

## Eurek Server 对于像 Client 注册、状态修改等写操作添加的都是读锁，而对于像增量下载请求添加的是写锁，为什么？为什么对于续约请求这种写操作处理中没有添加读锁？ 为什么全量下载中没有添加写锁？对于 Eureka Server 中添加锁的方式，请谈一下你的认识。

总体来说，这种读/写锁的添加方式就是为了解决两个共享集合 recentlyChangedQueue 与注册表 registry 的“集合迭代稳定性”问题。即增加读锁是为了限制其增加写锁，而增加写锁也是为了限制其增加读/写锁。若仅是考虑到 recentlyChangedQueue 集合的迭代稳定性问题，完全可以在处理注册、状态修改、删除 overridden 状态、下架、续约等写操作请求时添加写锁，而在处理全量下载、增量下载请求时添加读锁。

但这样做对于注册表 registry 集合来说就会出现问题。由于续约请求是一个发生频率非常高的写操作处理，若为其添加了写锁，则意味着在进行续约处理时，其它任何对 registry 的读/写操作均将阻塞。所以，续约处理是不能加写锁的。那为其添加读锁是否可以呢？也不行。因为对于这么一个发生频率很高的处理，若添加了读锁，那么，几乎这个 registry 就会被读锁给锁定，其它任何写操作均将被阻塞。所以，续约处理不能加锁。

处理注册、状态修改、删除 overridden 状态、下架等写操作请求时，为什么要添加读锁呢？添加写锁不行吗？若添加写锁，则意味着，任意一个对 registry 的写操作请求处理，均将阻塞所有其它对 registry 的读/写操作，效率非常低。而若这些写操作添加的是共享锁读锁， 则意味着，这些写操作可以同时进行。即使可能会出现对这些写操作同时操作同一个 registry 中的相同 instanceInfo 的情况，也不会出现问题。因为 registry 及 recentlyChangedQueue 都 是 JUC 的，是线程安全的。

由于那些写操作添加了读锁，所以增量下载这种读操作添加了写锁，以保证对共享集合读/写操作的互斥。

为什么增量下载添加了写锁，而全量下载没有添加呢？因为增量下载中没有涉及对共享集合注册表 registry 的操作，而全量下载读取了 registry。若为全量下载添加写锁，则必然会导致其在读取期间出现续约请求处理被阻塞的情况。对于这种频率非常高的续约处理是不能停止的。

## Eurek Server 对于续约过期的 Client 会定期进行清除，这个定时任务是何时启动的？ 由谁来完成的？又做了些什么？请谈一下你的认识。

Eurek Server 对于续约过期的 Client 的定时清除任务是在 Eureka Server 启动时专门创建了一个新的线程来执行的。确切地说，是 EurekaServerAutoConfiguration 在做实例化过程中完成的该定时任务线程的创建、执行。

这个定时任务首先查看了自我保护模型是否已经开启，若已经开启则不进行清除。若没有开启，则首先会将所有失联的 instance 集中到一个集合中，然后再计算“为了不启动自我保护模型，最多只能清除多少个 instance”，在进行清除时不能越过此最大值。

## Eurek Server 对于续约过期的 Client 会定期进行清除时有一个“补偿时间”概念。什么是补偿时间，请谈一下你的认识。

对于“补偿时间”的理解，首先要清楚 repeated 定时器的执行原理。本次任务的执行条件有两个，这两个条件必须同时满足才会执行任务。一是，上次任务执行完毕，二是， 按照配置文件中设置的清除间隔，本次任务的执行时间点也已经到了或过了。

那么什么是“补偿时间”呢？举例来说。

例如，正常情况下，若每 5s 清除一次过期对象，而清除一次需要 2s，则在第 5s 时开始清除 0s-5s 期间的过期对象。第 10s 开始清除 5s-10s 期间的过期对象。

若清除操作需要 6s，则在第 5s 时会开始清除，其清除的是 0s-5s 期间的过期对象。然而这次清除用时 6s，也就是说，在第 11s 时才清除完毕 0s-5s 期间的过期对象。按理说应该在第 10s 时开始清除 5s-10s 期间的过期对象，但由于上次的清除任务还未结束，所以在第10s 时不能开始清除，而在第 11s 时开始清除操作。因为已经过了 10s 这个时间点。此时要清除的对象就应该是 5s-11s 期间过期的，而多出的那 1s 就是需要补偿的时间。

## Eurek Server 对于续约过期的 Client 会定期进行清除，而这个清除过程中，系统会从过期对象数量 expiredLeases.size，与开启自我保护的阈值数量 evictionLimit 两个数值中选择一个最小的数进行清除。根据这个最小值进行清除，是不是会导致自我保护模式永远无法启动的情况？若出现了很多的过期对象，即这个 expiredLeases.size 很大，而 evictionLimit 是固定的。那么其清除的一定是 evictionLimit 个过期对象。这样的话是否自我保护模型永远无法启动？

答案是否定的。自我保护模式的启动并不是由这个清除任务决定的，而是由其它线程决定。只要发现收到的续约数据低于阈值了，那么就会启动自我保护模式。这里选择最少的进行清除，是为了尽量少些清除，给那些没有被清除的对象以“改过自新，浪子回头”的机会。可能由于网络抖动导致的失联，网络现在好了，其还可以恢复。若直接清除掉，那么就没有这个恢复的机会了。

 

 

 

