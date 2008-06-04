<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     version="1.0">

	<xsl:output method="text" omit-xml-declaration="yes"/> 
	<xsl:strip-space elements="*"/>

   <xsl:template match="item">
     <xsl:apply-templates select="name"/>
   </xsl:template>

   <xsl:template match="name">
     <xsl:value-of select="text()"/>
   </xsl:template>

 </xsl:stylesheet>