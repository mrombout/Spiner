<?xml version="1.0"?>
<?altova_samplexml file:///E:/Writing/TurnToPassage/format.xml?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/tw-storydata">
		<!-- Passages -->
		<xsl:call-template name="passage-files" />
		
		<!-- OPF -->
		<xsl:call-template name="opf-file" />
		
		<!-- NCX -->
		<xsl:call-template name="ncx-file" />
	</xsl:template>
	
	<xsl:template name="passage-files">
		<xsl:apply-templates select="tw-passagedata" mode="xhtml-file" />
	</xsl:template>
	
	<xsl:template name="opf-file">
		<xsl:result-document href="OEBPS/content.opf">
			<package version="2.0" xmlns="http://www.idpf.org/2007/opf" unique-identifier="TODO-UNIQID">
				<metadata xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:opf="http://www.idpf.org/2007/opf">
					<dc:title><xsl:value-of select="./@name" /></dc:title>
					<dc:language>en</dc:language>
					<dc:identifier id="TODO-UNIQID" opf:scheme="ISBN">TODO-ISBN</dc:identifier>
					<dc:creator opf:file-as="TODO-LASTNAME, TODO-FIRSTNAME" opf:role="aut">TODO-NAME</dc:creator>
				</metadata>
				
				<manifest>
					<xsl:apply-templates select="tw-passagedata" mode="opf-manifest" />
					<item id="ncx" href="toc.ncx" media-type="application/x-dtbncx+xml"/>
				</manifest>
				
				<spine toc="ncx">
					<xsl:apply-templates select="tw-passagedata" mode="opf-spine" />
				</spine>
				
				<guide>
				</guide>
			</package>
		</xsl:result-document>
	</xsl:template>
	
	<xsl:template name="ncx-file">
		<xsl:result-document href="OEBPS/toc.ncx">
			<ncx version="2005-1" xml:lang="en" xmlns="http://www.daisy.org/z3986/2005/ncx/">
				<head> 
					<meta name="dtb:uid" content="TODO-UNIQID"/>
					<meta name="dtb:depth" content="1"/>
					<meta name="dtb:totalPageCount" content="0"/>
					<meta name="dtb:maxPageNumber" content="0"/>
				</head>
				<docTitle>
					<text><xsl:value-of select="./@name" /></text>
				</docTitle>
				<docAuthor>
					<text><xsl:value-of select="./@creator" /></text>
				</docAuthor>
				<navMap>
					<xsl:apply-templates select="tw-passagedata" mode="ncx" />
				</navMap>
			</ncx>
		</xsl:result-document>
	</xsl:template>
	
	<xsl:template match="tw-passagedata" mode="xhtml-file">
		<xsl:result-document href="OEBPS/passage_{@name}.xhtml">
			<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
				<head>
					<meta http-equiv="content-type" content="application/xhtml+xml; charset=utf-8"/>
					<title>
						<xsl:value-of select="./@name"/>
					</title>
				</head>
				<body>
					<!--<xsl:value-of select="replace(., '\[{2}([\w\s]+)\|([\w\s]+)\]{2}', '&lt;a&gt;$2&lt;/a&gt;')" disable-output-escaping="yes" />-->
					<xsl:analyze-string select="." regex="\[{{2}}([\w\s]+)\|([\w\s]+)\]{{2}}">
						<xsl:matching-substring>
							<a href="passage_{regex-group(1)}.xhtml"><xsl:value-of select="regex-group(2)" /></a>
						</xsl:matching-substring>
						<xsl:non-matching-substring>
							<xsl:analyze-string select="." regex="\[{{2}}([\w\s]+)\]{{2}}">
								<xsl:matching-substring>
									<a href="passage_{regex-group(1)}.xhtml"><xsl:value-of select="regex-group(1)" /></a>
								</xsl:matching-substring>
								<xsl:non-matching-substring>
									<xsl:value-of select="." />
								</xsl:non-matching-substring>
							</xsl:analyze-string>
						</xsl:non-matching-substring>
					</xsl:analyze-string>
				</body>
			</html>
		</xsl:result-document>
	</xsl:template>
	
	<xsl:template match="tw-passagedata" mode="ncx">
		<navPoint class="chapter" id="passage_{@name}" playOrder="{@pid}">
			<navLabel><text>Passage <xsl:value-of select="@pid" />: <xsl:value-of select="@name" /></text></navLabel>
			<content src="passage_{@name}.xhtml"/>
		</navPoint>	
	</xsl:template>
	
	<xsl:template match="tw-passagedata" mode="opf-manifest">
		<item id="passage_{@name}" href="passage_{@name}.xhtml" media-type="application/xhtml+xml"/>
	</xsl:template>
	
	<xsl:template match="tw-passagedata" mode="opf-spine">
		<itemref idref="passage_{@name}" />
	</xsl:template>
</xsl:stylesheet>
