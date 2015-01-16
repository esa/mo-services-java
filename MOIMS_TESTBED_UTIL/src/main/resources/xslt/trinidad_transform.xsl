<?xml version="1.0" encoding="UTF-8"?>

  <!--
    Licensed under the European Space Agency Public License, Version 2.0
    You may not use this file except in compliance with the License.

    Except as expressly set forth in this License, the Software is provided to
    You on an "as is" basis and without warranties of any kind, including without
    limitation merchantability, fitness for a particular purpose, absence of
    defects or errors, accuracy or non-infringement of intellectual property rights.
 
    See the License for the specific language governing permissions and limitations under the License.
-->

<xsl:stylesheet version="1.0" xmlns:xhtml="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xalan="http://xml.apache.org/xslt" xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <xsl:output method="xhtml" indent="yes" xalan:indent-amount="2"/>

  <xsl:template match='xhtml:div[@class="collapsible"]'>
    <xsl:apply-templates select='xhtml:div'/>
  </xsl:template>

  <xsl:template match='xhtml:tr[xhtml:TD/xhtml:div]'>
    <xsl:copy>
      <xsl:apply-templates select="@*" />
    </xsl:copy>
    <xsl:copy>
      <xsl:apply-templates select="xhtml:td" />
    </xsl:copy>
    <xsl:variable name="rowspan">
      <xsl:for-each select="../xhtml:tr">
        <xsl:sort select="count(*)" data-type="number" order="descending"/>
        <xsl:if test="position()=1">
          <xsl:value-of select="count(*)"/>
        </xsl:if>
      </xsl:for-each>
    </xsl:variable>
    <xsl:copy>
      <xsl:element name="td">
        <xsl:attribute name="colspan">
          <xsl:value-of select="$rowspan"/>
        </xsl:attribute>
        <xsl:apply-templates select="xhtml:TD/xhtml:div/xhtml:div/xhtml:table" />
      </xsl:element>
    </xsl:copy>
  </xsl:template>

  <xsl:template match='xhtml:tr[xhtml:td="scenario"]'>
  </xsl:template>

  <xsl:template match='xhtml:tr[@class="scenario-detail closed-detail"]'>
    <tr>
      <xsl:apply-templates select='node()'/>
    </tr>
  </xsl:template>

  <xsl:template match='xhtml:script'>
    <xsl:apply-templates select='xhtml:div[@class="hidden"]'/>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="collapsible closed"]'>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="collapsible invisible"]'>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="header"]'>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="collapse_rim"]'>
    <xsl:apply-templates select='xhtml:div[@class="collapsable"]'/>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="hidden"]'>
    <xsl:apply-templates select='xhtml:pre'/>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="sidebar"]'>
  </xsl:template>

  <xsl:template match='xhtml:div[@class="mainbar"]'>
    <xsl:apply-templates select='node()|@*'/>
  </xsl:template>

  <xsl:template match='@class'>
    <xsl:choose>
      <xsl:when test='string(self::node()) = "pass"'>
        <xsl:attribute name="style">background-color: #AAFFAA</xsl:attribute>
      </xsl:when>
      <xsl:when test='string(self::node()) = "fail"'>
        <xsl:attribute name="style">background-color: FFAAAA</xsl:attribute>
      </xsl:when>
      <xsl:when test='string(self::node()) = "error"'>
        <xsl:attribute name="style">background-color: FFFFAA</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>