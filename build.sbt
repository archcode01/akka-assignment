import sbt._

name := "testproject"

version := "1.0"

scalaVersion := "2.12.10"

val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.2.1"
val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.6.10"
val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.1"
val akkaStreamContrib = "com.typesafe.akka" %% "akka-stream-contrib" % "0.11"
val scalaTest    = "org.scalatest"     %% "scalatest"         % "3.0.1" % "test"
val specs = "org.specs2"    %% "specs2"    % "2.2.3" % "test"
val wiremock =   "com.github.tomakehurst" % "wiremock" % "1.33" % "test"


libraryDependencies ++= Seq(akkaHttp,akkaStream,akkaHttpSprayJson,akkaStreamContrib, scalaTest,specs,wiremock)
//
//
//val aopMerge: MergeStrategy = new MergeStrategy {
//  val name = "aopMerge"
//  import scala.xml._
//  import scala.xml.dtd._
//
//  def apply(tempDir: File, path: String, files: Seq[File]): Either[String, Seq[(File, String)]] = {
//    val dt                         = DocType("aspectj", PublicID("-//AspectJ//DTD//EN", "http://www.eclipse.org/aspectj/dtd/aspectj.dtd"), Nil)
//    val file                       = MergeStrategy.createMergeTarget(tempDir, path)
//    val xmls: Seq[Elem]            = files.map(XML.loadFile)
//    val aspectsChildren: Seq[Node] = xmls.flatMap(_ \\ "aspectj" \ "aspects" \ "_")
//    val weaverChildren: Seq[Node]  = xmls.flatMap(_ \\ "aspectj" \ "weaver" \ "_")
//    val options: String            = xmls.map(x => (x \\ "aspectj" \ "weaver" \ "@options").text).mkString(" ").trim
//    val weaverAttr                 = if (options.isEmpty) Null else new UnprefixedAttribute("options", options, Null)
//    val aspects                    = new Elem(null, "aspects", Null, TopScope, false, aspectsChildren: _*)
//    val weaver                     = new Elem(null, "weaver", weaverAttr, TopScope, false, weaverChildren: _*)
//    val aspectj                    = new Elem(null, "aspectj", Null, TopScope, false, aspects, weaver)
//    XML.save(file.toString, aspectj, "UTF-8", xmlDecl = false, dt)
//    IO.append(file, IO.Newline.getBytes(IO.defaultCharset))
//    Right(Seq(file -> path))
//  }
//}
//logBuffered in Test := false
//
//fork in Test := true
//
////Discarding io.netty.versions.properties as multiple jars have it.
//// Do we need the io.netty.versions.properties? Don't know yet. It got added  by dependency graph
//assemblyMergeStrategy in assembly := {
//  case PathList(ps @ _*) if ps.last endsWith "aop.xml" =>
//    aopMerge
//  case PathList(ps @ _*) if ps.last endsWith "io.netty.versions.properties" =>
//    MergeStrategy.first
//  case PathList(ps @ _*) if ps.last endsWith "logback.xml" =>
//    MergeStrategy.first //To load logback file from service
//  case x =>
//    val oldStrategy = (assemblyMergeStrategy in assembly).value
//    oldStrategy(x)
//}
