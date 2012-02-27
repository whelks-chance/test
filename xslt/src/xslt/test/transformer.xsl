<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="outer">
        Look its an xml outer <xsl:value-of select="list"/>
        <xsl:apply-templates select="inner"/>
    </xsl:template>

    <xsl:template match="inner">
        An inner sublist of <xsl:value-of select="@Name"/>
        <xsl:apply-templates select="object"/>
    </xsl:template>

    <xsl:template match="object">
        An object with name <xsl:value-of select="text()"/>
    </xsl:template>

</xsl:stylesheet>