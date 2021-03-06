###wsdl文件结构分析
####1.WSDL介绍
 (Web Services Description Language,Web服务描述语言)是一种XML Application，他将Web服务描述定义为一组服务访问点，客户端可以通过这些服务访问点对包含面向文档信息或面向过程调用的服务进行访问(类似远程过程调用)。
 WSDL首先对访问的操作和访问时使用的请求/响应消息进行抽象描述，
 然后将其绑定到具体的传输协议和消息格式上以最终定义具体部署的服务访问点。相关的具体部署的服务访问点通过组合就成为抽象的Web服务。 本文将详细讲解WSDL文档的结构，并分析每个元素的作用。
 2.一：WSDL定义
   
       WSDL是一个用于精确描述Web服务的文档，WSDL文档是一个遵循WSDL XML模式的XML文档。WSDL 文档将Web服务定义为服务访问点或端口的集合。在 WSDL 中，由于服务访问点和消息的抽象定义已从具体的服务部署或数据格式绑定中分离出来，因此可以对抽象定义进行再次使用：消息，指对交换数据的抽象描述；而端口类型，指操作的抽象集合。用于特定端口类型的具体协议和数据格式规范构成了可以再次使用的绑定。将Web访问地址与可再次使用的绑定相关联，可以定义一个端口，而端口的集合则定义为服务。
   
      一个WSDL文档通常包含7个重要的元素，即types、import、message、portType、operation、binding、 service元素。这些元素嵌套在definitions元素中，definitions是WSDL文档的根元素。文章的下一部分将会详细介绍WSDL 的基本结构。