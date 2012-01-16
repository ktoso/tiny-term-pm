package pl.project13.tinytermpm.model

import xml.{Node, NodeSeq, XML}


trait ContainerExtractor[ElementType] {
  def elementName: String
  def elementType: PartialFunction[NodeSeq, ElementType]

  def apply(xml: String): List[ElementType] = apply(XML.loadString(xml))
  def apply(xml: NodeSeq): List[ElementType] = (xml \\ elementName).map { elementType.apply(_) }.toList
}