<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output  method="xml" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <root>
            <xsl:for-each select="root/movie">
                <movie>
                    <xsl:copy-of select="id"/>
                    <xsl:copy-of select="title"/>
                    <xsl:copy-of select="directors"/>
                    <xsl:copy-of select="rate"/>
                    <xsl:copy-of select="casts"/>
                    <xsl:copy-of select="type"/>
                    <xsl:copy-of select="nation"/>
                    <xsl:copy-of select="language"/>
                    <xsl:copy-of select="date"/>
                    <xsl:copy-of select="time"/>
                    <xsl:copy-of select="peopleNumber"/>
                    <xsl:copy-of select="introduction"/>

                    <hotComments>
                        <xsl:for-each select="hotComments/hotComment">
                            <hotComment>
                                <xsl:copy-of select="hotCommentAuthor"/>
                                <xsl:copy-of select="hotCommentDate"/>
                                <xsl:copy-of select="hotCommentContent"/>
                            </hotComment>
                        </xsl:for-each>
                    </hotComments>

                </movie>
            </xsl:for-each>
        </root>

    </xsl:template>

</xsl:stylesheet>