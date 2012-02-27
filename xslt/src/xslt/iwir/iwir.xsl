<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="IWIR">

        <tool>
            <toolname><xsl:value-of select="@wfname"/></toolname>

            <package/>

            <version><xsl:value-of select="@version"/></version>

            <inportnum>
                <xsl:value-of select="count(//inputport)"/>
            </inportnum>

            <outportnum>
                <xsl:value-of select="count(//inputport)"/>
            </outportnum>

        </tool>
        <xsl:apply-templates select="parallelForEach"/>
    </xsl:template>

    <xsl:template match="parallelForEach">
        A parallel for each
        <xsl:apply-templates select="inputports"/>
        <xsl:apply-templates select="outputports"/>
        <xsl:apply-templates select="body"/>
        <xsl:apply-templates select="links"/>
    </xsl:template>

    <xsl:template match="inputports">
        some inputs
    </xsl:template>

    <xsl:template match="outputports">
        some outputs
    </xsl:template>

    <xsl:template match="body">
        body
    </xsl:template>

    <xsl:template match="links">
        links
    </xsl:template>

</xsl:stylesheet>