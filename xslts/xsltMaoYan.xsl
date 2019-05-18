<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output  method="xml" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <root>
            <xsl:for-each select="root/maoYanFilm">
                <maoYanFilm>
                    <xsl:copy-of select="filmInfo/name"/>
                    <xsl:copy-of select="filmInfo/director"/>
                    <xsl:copy-of select="filmInfo/type"/>
                    <xsl:copy-of select="filmInfo/filmingLocation"/>
                    <xsl:copy-of select="filmInfo/duration"/>
                    <xsl:copy-of select="filmInfo/releasedTime"/>
                    <xsl:copy-of select="filmInfo/score"/>

                    <xsl:copy-of select="filmDescription/description"/>

                    <xsl:copy-of select="filmComments">
                    </xsl:copy-of>
                </maoYanFilm>
            </xsl:for-each>
        </root>

    </xsl:template>
</xsl:stylesheet>