<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output  method="xml" omit-xml-declaration="yes"/>
<xsl:variable name="file2" select="document('file2.xml')"/><xsl:template match="/">

	    <staffs>
        <xsl:for-each select="/staffs/staff">
        <xsl:variable name="CurrentOrder"><xsl:value-of select = "attribute::order" /></xsl:variable>
        	<staff>
        		<xsl:attribute name="order"><xsl:value-of select="@order" /></xsl:attribute>
        		<xsl:copy-of select="name" />
        		<xsl:copy-of select="age" />
        		<xsl:copy-of select="$file2/staffs/staff[@order=$CurrentOrder]/Money" />
        	</staff>
        </xsl:for-each>
    </staffs>
</xsl:template>
</xsl:stylesheet>