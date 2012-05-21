<?xml version="1.0"?>

<xsl:stylesheet xmlns:gt="http://globaltester.org/testspecification"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0" xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:ooo="http://openoffice.org/2004/office" xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:oooc="http://openoffice.org/2004/calc" xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rpt="http://openoffice.org/2005/report"
	xmlns:of="urn:oasis:names:tc:opendocument:xmlns:of:1.2" xmlns:xhtml="http://www.w3.org/1999/xhtml"
	xmlns:grddl="http://www.w3.org/2003/g/data-view#" xmlns:tableooo="http://openoffice.org/2009/table"
	xmlns:field="urn:openoffice:names:experimental:ooo-ms-interop:xmlns:field:1.0"

	version="1.0">

	<xsl:output method="xml" indent="yes" />

	<xsl:param name="document.title" />
	<xsl:param name="document.subtitle" />
	<xsl:param name="company.name" />
	<xsl:param name="company.address1" />
	<xsl:param name="company.address2" />
	<xsl:param name="company.tel" />
	<xsl:param name="company.mail" />
	<xsl:param name="company.www" />
	<xsl:param name="year" />

	<xsl:template match="gt:TestSpecification">

		<!-- Start of ODT file structure -->
		<office:document-content office:version="1.2"
			grddl:transformation="http://docs.oasis-open.org/office/1.2/xslt/odf2rdf.xsl">
			<office:scripts />
			<office:font-face-decls>
				<style:font-face style:name="StarSymbol"
					svg:font-family="StarSymbol" style:font-charset="x-symbol" />
				<style:font-face style:name="Lucidasans1"
					svg:font-family="Lucidasans" />
				<style:font-face style:name="Times New Roman"
					svg:font-family="&apos;Times New Roman&apos;" />
				<style:font-face style:name="Courier New"
					svg:font-family="&apos;Courier New&apos;"
					style:font-family-generic="modern" />
				<style:font-face style:name="Times New Roman2"
					svg:font-family="&apos;Times New Roman&apos;, &apos;Times New Roman PSMT&apos;"
					style:font-family-generic="roman" />
				<style:font-face style:name="Courier New1"
					svg:font-family="&apos;Courier New&apos;"
					style:font-family-generic="modern" style:font-pitch="fixed" />
				<style:font-face style:name="Lucidasans"
					svg:font-family="Lucidasans" style:font-pitch="variable" />
				<style:font-face style:name="Mincho"
					svg:font-family="Mincho" style:font-pitch="variable" />
				<style:font-face style:name="Tahoma"
					svg:font-family="Tahoma" style:font-pitch="variable" />
				<style:font-face style:name="Times New Roman1"
					svg:font-family="&apos;Times New Roman&apos;"
					style:font-adornments="Standard" style:font-pitch="variable" />
				<style:font-face style:name="Times New Roman3"
					svg:font-family="&apos;Times New Roman&apos;"
					style:font-adornments="Fett" style:font-family-generic="roman"
					style:font-pitch="variable" />
				<style:font-face style:name="Arial" svg:font-family="Arial"
					style:font-family-generic="swiss" style:font-pitch="variable" />
				<style:font-face style:name="Arial1"
					svg:font-family="Arial" style:font-adornments="Standard"
					style:font-family-generic="swiss" style:font-pitch="variable" />
				<style:font-face style:name="Andale Sans UI"
					svg:font-family="&apos;Andale Sans UI&apos;"
					style:font-family-generic="system" style:font-pitch="variable" />
				<style:font-face style:name="Tahoma1"
					svg:font-family="Tahoma" style:font-family-generic="system"
					style:font-pitch="variable" />
			</office:font-face-decls>
			<office:automatic-styles>
				<style:style style:name="P689" style:family="paragraph"
					style:parent-style-name="Standard" style:list-style-name="L2">
					<style:text-properties style:font-name="Courier New1" />
				</style:style>
				<text:list-style style:name="L675">
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="1" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="1.27cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="1.27cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="2" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="1.905cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="1.905cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="3" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="2.54cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="2.54cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="4" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="3.175cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="3.175cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="5" style:num-format="1">
						-
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="3.81cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="3.81cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="6" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="4.445cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="4.445cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					-
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="7" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="5.08cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="5.08cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="8" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="5.715cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="5.715cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="9" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="6.35cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="6.35cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
					<text:list-level-style-number
						style:num-suffix="." text:style-name="Numbering_20_Symbols"
						text:level="10" style:num-format="1">
						<style:list-level-properties
							text:list-level-position-and-space-mode="label-alignment">
							<style:list-level-label-alignment
								fo:margin-left="6.985cm" fo:text-indent="-0.635cm"
								text:list-tab-stop-position="6.985cm" text:label-followed-by="listtab" />
						</style:list-level-properties>
					</text:list-level-style-number>
				</text:list-style>
				<style:style style:name="Tabelle1" style:family="table">
					<style:table-properties style:width="17cm"
						table:align="margins" />
				</style:style>
				<style:style style:name="Tabelle1.A" style:family="table-column">
					<style:table-column-properties
						style:column-width="2.581cm" style:rel-column-width="9947*" />
				</style:style>
				<style:style style:name="Tabelle1.B" style:family="table-column">
					<style:table-column-properties
						style:column-width="14.42cm" style:rel-column-width="55588*" />
				</style:style>
				<style:style style:name="Tabelle1.A1" style:family="table-cell">
					<style:table-cell-properties
						fo:background-color="#cccccc" fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="none" fo:border-top="0.002cm solid #000000"
						fo:border-bottom="0.002cm solid #000000">
						<style:background-image />
					</style:table-cell-properties>
				</style:style>
				<style:style style:name="Tabelle1.B1" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="Tabelle1.A2" style:family="table-cell">
					<style:table-cell-properties
						fo:background-color="#cccccc" fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="none" fo:border-top="none" fo:border-bottom="0.002cm solid #000000">
						<style:background-image />
					</style:table-cell-properties>
				</style:style>
				<style:style style:name="Tabelle1.B2" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="0.002cm solid #000000" fo:border-top="none"
						fo:border-bottom="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="Tabelle25" style:family="table">
					<style:table-properties style:width="17cm"
						table:align="margins" />
				</style:style>
				<style:style style:name="Tabelle25.A" style:family="table-column">
					<style:table-column-properties
						style:column-width="1.97cm" style:rel-column-width="7595*" />
				</style:style>
				<style:style style:name="Tabelle25.B" style:family="table-column">
					<style:table-column-properties
						style:column-width="2.409cm" style:rel-column-width="9288*" />
				</style:style>
				<style:style style:name="Tabelle25.C" style:family="table-column">
					<style:table-column-properties
						style:column-width="8.37cm" style:rel-column-width="32264*" />
				</style:style>
				<style:style style:name="Tabelle25.D" style:family="table-column">
					<style:table-column-properties
						style:column-width="4.251cm" style:rel-column-width="16388*" />
				</style:style>
				<style:style style:name="Tabelle25.A1" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="none" fo:border-top="0.002cm solid #000000"
						fo:border-bottom="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="Tabelle25.D1" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="Tabelle25.A2" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="none" fo:border-top="none" fo:border-bottom="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="Tabelle25.D2" style:family="table-cell">
					<style:table-cell-properties
						fo:padding="0.097cm" fo:border-left="0.002cm solid #000000"
						fo:border-right="0.002cm solid #000000" fo:border-top="none"
						fo:border-bottom="0.002cm solid #000000" />
				</style:style>
				<style:style style:name="P1" style:family="paragraph"
					style:parent-style-name="Header">
					<style:paragraph-properties
						fo:text-align="start" style:justify-single-word="false" />
				</style:style>
				<style:style style:name="P2" style:family="paragraph"
					style:parent-style-name="Header">
					<style:paragraph-properties
						fo:text-align="end" style:justify-single-word="false" />
				</style:style>
				<style:style style:name="P3" style:family="paragraph"
					style:parent-style-name="Header">
					<style:paragraph-properties>
						<style:tab-stops>
							<style:tab-stop style:position="8.498cm"
								style:type="center" />
							<style:tab-stop style:position="25.698cm"
								style:type="right" />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P4" style:family="paragraph"
					style:parent-style-name="Footer">
					<style:paragraph-properties
						fo:text-align="end" style:justify-single-word="false" />
				</style:style>
				<style:style style:name="P5" style:family="paragraph"
					style:parent-style-name="Footer">
					<style:paragraph-properties
						fo:padding-left="0cm" fo:padding-right="0cm" fo:padding-top="0.035cm"
						fo:padding-bottom="0cm" fo:border-left="none" fo:border-right="none"
						fo:border-top="0.018cm solid #000000" fo:border-bottom="none">
						<style:tab-stops>
							<style:tab-stop style:position="18cm" style:type="right" />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P6" style:family="paragraph"
					style:parent-style-name="Footer">
					<style:paragraph-properties
						fo:text-align="start" style:justify-single-word="false"
						fo:padding-left="0cm" fo:padding-right="0cm" fo:padding-top="0.035cm"
						fo:padding-bottom="0cm" fo:border-left="none" fo:border-right="none"
						fo:border-top="0.018cm solid #000000" fo:border-bottom="none">
						<style:tab-stops>
							<style:tab-stop style:position="18cm" style:type="right" />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P7" style:family="paragraph"
					style:parent-style-name="Fußzeile-Querformat">
					<style:paragraph-properties>
						<style:tab-stops>
							<style:tab-stop style:position="25.718cm"
								style:type="right" />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P8" style:family="paragraph"
					style:parent-style-name="Table_20_Contents">
					<style:text-properties fo:font-weight="bold"
						style:font-weight-asian="bold" style:font-weight-complex="bold" />
				</style:style>
				<style:style style:name="P9" style:family="paragraph"
					style:parent-style-name="Standard">
					<style:paragraph-properties
						fo:margin-top="0cm" fo:margin-bottom="0cm" />
				</style:style>
				<style:style style:name="P10" style:family="paragraph"
					style:parent-style-name="Bibliography_20_1">
					<style:paragraph-properties>
						<style:tab-stops>
							<style:tab-stop style:position="3cm" />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P11" style:family="paragraph"
					style:parent-style-name="Standard">
					<style:paragraph-properties
						fo:margin-top="5.001cm" fo:margin-bottom="0cm" />
				</style:style>
				<style:style style:name="P12" style:family="paragraph"
					style:parent-style-name="Contents_20_1">
					<style:paragraph-properties>
						<style:tab-stops>
							<style:tab-stop style:position="-1cm" />
							<style:tab-stop style:position="18cm" style:type="right"
								style:leader-style="dotted" style:leader-text="." />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P13" style:family="paragraph"
					style:parent-style-name="Contents_20_2">
					<style:paragraph-properties>
						<style:tab-stops>
							<style:tab-stop style:position="-1.499cm" />
							<style:tab-stop style:position="18.5cm"
								style:type="right" style:leader-style="dotted"
								style:leader-text="." />
						</style:tab-stops>
					</style:paragraph-properties>
				</style:style>
				<style:style style:name="P14" style:family="paragraph"
					style:parent-style-name="Standard" style:master-page-name="_5f_Vorwort_5f_P_5f_Rückseite">
					<style:paragraph-properties
						style:page-number="auto" />
				</style:style>
				<style:style style:name="P15" style:family="paragraph"
					style:parent-style-name="Heading_20_1" style:master-page-name="Right_20_Page">
					<style:paragraph-properties
						style:page-number="auto" />
				</style:style>
				<style:style style:name="P16" style:family="paragraph"
					style:parent-style-name="Contents_20_Heading"
					style:master-page-name="Index">
					<style:paragraph-properties
						style:page-number="auto" />
				</style:style>
				<style:style style:name="P17" style:family="paragraph"
					style:parent-style-name="Title" style:master-page-name="_5f_Deckblatt">
					<style:paragraph-properties
						style:page-number="auto" />
				</style:style>
				<style:style style:name="T1" style:family="text">
					<style:text-properties fo:language="it"
						fo:country="IT" />
				</style:style>
				<style:style style:name="T2" style:family="text">
					<style:text-properties fo:language="it"
						fo:country="IT" fo:background-color="#ff0000" />
				</style:style>
				<style:style style:name="T3" style:family="text">
					<style:text-properties fo:background-color="#ff0000" />
				</style:style>
				<style:style style:name="fr1" style:family="graphic"
					style:parent-style-name="Graphics">
					<style:graphic-properties
						style:vertical-pos="from-top" style:vertical-rel="paragraph"
						style:horizontal-pos="from-left" style:horizontal-rel="paragraph"
						style:shadow="none" style:mirror="none" fo:clip="rect(0cm, 0cm, 0cm, 0cm)"
						draw:luminance="0%" draw:contrast="0%" draw:red="0%" draw:green="0%"
						draw:blue="0%" draw:gamma="100%" draw:color-inversion="false"
						draw:image-opacity="100%" draw:color-mode="standard" />
				</style:style>
				<style:style style:name="Sect1" style:family="section">
					<style:section-properties
						fo:background-color="transparent" style:editable="false">
						<style:columns fo:column-count="1" fo:column-gap="0cm" />
						<style:background-image />
					</style:section-properties>
				</style:style>
				<style:style style:name="Sect2" style:family="section">
					<style:section-properties style:editable="false">
						<style:columns fo:column-count="1" fo:column-gap="0cm" />
					</style:section-properties>
				</style:style>
			</office:automatic-styles>




			<office:body>
				<office:text text:use-soft-page-breaks="true">
					<office:forms form:automatic-focus="false"
						form:apply-design-mode="false" />
					<text:sequence-decls>
						<text:sequence-decl text:display-outline-level="0"
							text:name="Illustration" />
						<text:sequence-decl text:display-outline-level="0"
							text:name="Table" />
						<text:sequence-decl text:display-outline-level="0"
							text:name="Text" />
						<text:sequence-decl text:display-outline-level="0"
							text:name="Drawing" />
					</text:sequence-decls>
					<text:user-field-decls>
						<text:user-field-decl office:value-type="string"
							office:string-value="11" text:name="SEQ Abbildung \* ARABIC" />
						<text:user-field-decl office:value-type="string"
							office:string-value="3" text:name="SEQ Tabelle \* ARABIC" />
					</text:user-field-decls>
					<text:alphabetical-index-auto-mark-file
						xlink:href="../../../../../tmp/ÖA-Stichwortverzeichnis-V01.sdi" />
					<text:p text:style-name="P17">
						<xsl:value-of select="$document.title"/>
					</text:p>
					<text:p text:style-name="Subtitle">
						<xsl:value-of select="$document.subtitle"></xsl:value-of></text:p>
					<text:p text:style-name="P11" />
					<text:p text:style-name="P11"><xsl:value-of select="$company.name"/></text:p>
					<text:p text:style-name="P9"><xsl:value-of select="$company.address1"/></text:p>
					<text:p text:style-name="P9"><xsl:value-of select="$company.address2"/></text:p>
					<text:p text:style-name="P9">
						<xsl:value-of select="$company.tel"/>
					</text:p>
					<text:p text:style-name="P9">
						<text:alphabetical-index-mark
							text:string-value="E-Mail (Electronic Mail)" />
						<text:span text:style-name="P9">E-Mail: <xsl:value-of select="$company.mail"/>
						</text:span>
					</text:p>
					<text:p text:style-name="P9">Internet:
						<xsl:value-of select="$company.www"/>
					</text:p>
					<text:p text:style-name="P9">
						<text:span text:style-name="P9">© <xsl:value-of select="$company.name"/> - <xsl:value-of select="$year"/></text:span>
					</text:p>
					<text:p text:style-name="P14" />
					<text:table-of-content text:style-name="Sect1"
						text:protected="true" text:name="Inhaltsverzeichnis1">
						<text:table-of-content-source
							text:outline-level="4" text:relative-tab-stop-position="false">
							<text:index-title-template
								text:style-name="Contents_20_Heading">Table of Contents</text:index-title-template>
							<text:table-of-content-entry-template
								text:outline-level="1" text:style-name="Contents_20_1">
								<text:index-entry-link-start
									text:style-name="Internet_20_link" />
								<text:index-entry-chapter />
								<text:index-entry-tab-stop style:type="left"
									style:position="0cm" style:leader-char=" " />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-link-end />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="2" text:style-name="Contents_20_2">
								<text:index-entry-chapter />
								<text:index-entry-tab-stop style:type="left"
									style:position="0cm" style:leader-char=" " />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="3" text:style-name="Contents_20_3">
								<text:index-entry-chapter />
								<text:index-entry-tab-stop style:type="left"
									style:position="0cm" style:leader-char=" " />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="4" text:style-name="Contents_20_4">
								<text:index-entry-chapter />
								<text:index-entry-tab-stop style:type="left"
									style:position="0cm" style:leader-char=" " />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="5" text:style-name="Contents_20_5">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="6" text:style-name="Contents_20_6">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="7" text:style-name="Contents_20_7">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="8" text:style-name="Contents_20_8">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="9" text:style-name="Contents_20_9">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
							<text:table-of-content-entry-template
								text:outline-level="10" text:style-name="Contents_20_10">
								<text:index-entry-chapter />
								<text:index-entry-text />
								<text:index-entry-tab-stop style:type="right"
									style:leader-char="." />
								<text:index-entry-page-number />
							</text:table-of-content-entry-template>
						</text:table-of-content-source>
						<text:index-body>
							<text:index-title text:style-name="Sect2"
								text:name="Inhaltsverzeichnis1_Head">
								<text:p text:style-name="P16">Table of Contents</text:p>
							</text:index-title>
							<text:p text:style-name="P12">
								<text:a xlink:type="simple" xlink:href="#__RefHeading__5546_763239732"
									text:style-name="Internet_20_link" text:visited-style-name="Internet_20_link">
									1
									<text:tab />
									Introduction
									<text:tab />
									5
								</text:a>
							</text:p>
							<text:p text:style-name="P12">
								<text:a xlink:type="simple" xlink:href="#__RefHeading__5558_763239732"
									text:style-name="Internet_20_link" text:visited-style-name="Internet_20_link">
									<text:tab />
									Annex
									<text:tab />
									6
								</text:a>
							</text:p>
							<text:p text:style-name="P13">
								<text:tab />
								References
								<text:tab />
								6
							</text:p>
						</text:index-body>
					</text:table-of-content>
					<text:p text:style-name="Standard" />
					<text:p text:style-name="Standard" />



					<!-- Handling of introduction -->
					<xsl:for-each select="gt:Text">
						<xsl:for-each select="*">
							<xsl:if test="name(.)='Paragraph'">
								<text:p text:style-name="Standard">
									<xsl:value-of select="." />
								</text:p>
							</xsl:if>
							<xsl:if test="name(.)='Heading'">
								<xsl:call-template name="Headline" />
							</xsl:if>
							<xsl:if test="name(.)='Table'">
								<xsl:call-template name="Table" />
							</xsl:if>
							<xsl:if test="name(.)='List'">
								<xsl:for-each select="./gt:Item">
									<text:p text:style-name="Standard">
										-
										<xsl:value-of select="." />
									</text:p>
								</xsl:for-each>
							</xsl:if>
						</xsl:for-each>
					</xsl:for-each>

					<!-- Handling of profiles -->
					<xsl:variable name="filenameProfiles" select="gt:TestProfiles" />
					<xsl:for-each select="document($filenameProfiles)">
						<!-- Introducing text paragraphs of profiles -->
						<xsl:for-each select="gt:Profiles/gt:Text">
							<xsl:for-each select="*">
								<xsl:if test="name(.)='Paragraph'">
									<text:p text:style-name="Standard">
										<xsl:value-of select="." />
									</text:p>
								</xsl:if>
								<xsl:if test="name(.)='Heading'">
									<xsl:call-template name="Headline"/>
								</xsl:if>
							</xsl:for-each>
						</xsl:for-each>

						<!-- Profiles -->
						<text:h text:style-name="Heading_20_3" text:outline-level="3">Application
							Profiles</text:h>
						<table:table>
							<table:table-column />
							<table:table-column />
							<table:table-column />
							<table:table-row>
								<table:table-cell>
									<text:p>Profile ID</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Profile Name</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Description</text:p>
								</table:table-cell>
							</table:table-row>

							<xsl:for-each select="gt:Profiles/gt:Profile[@type='Application Profile']">
								<table:table-row>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./@id" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Name" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Description" />
										</text:p>
									</table:table-cell>
								</table:table-row>
							</xsl:for-each>
						</table:table>

						<text:h text:style-name="Heading_20_3" text:outline-level="3">Protocol
							Profiles</text:h>
						<table:table>
							<table:table-column />
							<table:table-column />
							<table:table-column />
							<table:table-row>
								<table:table-cell>
									<text:p>Profile ID</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Profile Name</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Description</text:p>
								</table:table-cell>
							</table:table-row>

							<xsl:for-each select="gt:Profiles/gt:Profile[@type='Protocol Profile']">
								<table:table-row>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./@id" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Name" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Description" />
										</text:p>
									</table:table-cell>
								</table:table-row>
							</xsl:for-each>
						</table:table>

						<text:h text:style-name="Heading_20_3" text:outline-level="3">Algorithm
							Profiles</text:h>
						<table:table>
							<table:table-column />
							<table:table-column />
							<table:table-column />
							<table:table-row>
								<table:table-cell>
									<text:p>Profile ID</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Profile Name</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Description</text:p>
								</table:table-cell>
							</table:table-row>

							<xsl:for-each select="Profiles/Profile[@type='Algorithm Profile']">
								<table:table-row>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./@id" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Name" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Description" />
										</text:p>
									</table:table-cell>
								</table:table-row>
							</xsl:for-each>
						</table:table>

						<text:h text:style-name="Heading_20_3" text:outline-level="3">Data
							Group Profiles</text:h>
						<table:table>
							<table:table-column />
							<table:table-column />
							<table:table-column />
							<table:table-row>
								<table:table-cell>
									<text:p>Profile ID</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Profile Name</text:p>
								</table:table-cell>
								<table:table-cell>
									<text:p>Description</text:p>
								</table:table-cell>
							</table:table-row>

							<xsl:for-each select="Profiles/Profile[@type='Data Group Profile']">
								<table:table-row>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./@id" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Name" />
										</text:p>
									</table:table-cell>
									<table:table-cell>
										<text:p>
											<xsl:apply-templates select="./gt:Description" />
										</text:p>
									</table:table-cell>
								</table:table-row>
							</xsl:for-each>
						</table:table>

					</xsl:for-each>


					<!-- Handling of certificates -->
					<xsl:variable name="filenameCertificates" select="gt:CertificateDefinition" />
					<xsl:for-each select="document($filenameCertificates)">
						<!-- Introducing text paragraphs of certificates -->
						<xsl:for-each select="Certificates/Text">
							<xsl:for-each select="*">
								<xsl:if test="name(.)='Paragraph'">
									<text:p text:style-name="Standard">
										<xsl:value-of select="." />
									</text:p>
								</xsl:if>
								<xsl:if test="name(.)='Heading'">
									<xsl:call-template name="Headline"/>
								</xsl:if>
								<xsl:if test="name(.)='Table'">
									<xsl:call-template name="Table"/>
								</xsl:if>

							</xsl:for-each>
						</xsl:for-each>

						<!-- Certificates -->
						<xsl:for-each select="gt:Certificates/gt:Certificate">
							<xsl:variable name="filenameCertificate" select="." />
							<xsl:for-each select="document($filenameCertificate)">
								<text:h text:style-name="Heading_20_3"
									text:outline-level="3">
									Certificate <xsl:apply-templates select="gt:Certificate/@id" />
								</text:h>

								<table:table table:name="TabelleCertificate"
									table:style-name="Tabelle1">
									<table:table-column table:style-name="Tabelle1.A" />
									<table:table-column table:style-name="Tabelle1.B" />
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">ID</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<text:bookmark-start text:name="{Certificate/@id}"/><xsl:apply-templates select="gt:Certificate/@id" /><text:bookmark-end text:name="{Certificate/@id}"/>
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Purpose</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<xsl:apply-templates select="gt:Certificate/gt:Purpose" />
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Version</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<xsl:apply-templates select="gt:Certificate/gt:Version" />
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Content / Parameter</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<xsl:for-each select="gt:Certificate/gt:CertificateContent/*">
												<xsl:if
													test="name(.)='CertificateAuthorityReference' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														CAR:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if
													test="name(.)='SignerCertificate' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														Signer:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if test="name(.)='SigningKey' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														Signing Key:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if
													test="name(.)='CertificateHolderReference' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														CHR:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if
													test="name(.)='CertificateHolderAuthorization' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														CHA:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if
													test="name(.)='CertificateEffectiveDate' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														Eff. Date:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if
													test="name(.)='CertificateExpirationDate' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														Exp. Date:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
												<xsl:if test="name(.)='PublicKey' and string-length(.)>=1">
													<text:p text:style-name="Table_20_Contents">
														Public Key:
														<xsl:value-of select="." />
													</text:p>
												</xsl:if>
											</xsl:for-each>
										</table:table-cell>
									</table:table-row>

									<!-- Check for modifications -->
									<xsl:for-each select="gt:Certificate/gt:CertificateModification">
										<table:table-row>
											<table:table-cell table:style-name="Tabelle1.A1"
												office:value-type="string">
												<text:p text:style-name="P8">Modification</text:p>
											</table:table-cell>
											<table:table-cell table:style-name="Tabelle1.B1"
												office:value-type="string">
												<text:p text:style-name="Table_20_Contents">
													<xsl:value-of select="./gt:Description" />
												</text:p>
											</table:table-cell>
										</table:table-row>
									</xsl:for-each>

								</table:table>

							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>


					<!-- Handling of routines -->
					<xsl:variable name="filenameRoutines" select="gt:Routines" />

					<xsl:for-each select="document($filenameRoutines)">
						<!-- Introducing text paragraphs of routines -->
						<xsl:for-each select="gt:Routines/gt:Text">
							<xsl:for-each select="*">
								<xsl:if test="name(.)='Paragraph'">
									<text:p text:style-name="Standard">
										<xsl:value-of select="." />
									</text:p>
								</xsl:if>
								<xsl:if test="name(.)='Heading'">
									<xsl:call-template name="Headline"/>
								</xsl:if>
							</xsl:for-each>
						</xsl:for-each>

						<!-- Routines -->
						<xsl:for-each select="gt:Routines/gt:Routine">
							<xsl:variable name="filenameRoutine" select="." />
							<xsl:for-each select="document($filenameRoutine)">
								<text:h text:style-name="Heading_20_2"
									text:outline-level="2">
									Routine
									<xsl:apply-templates select="gt:Routine/gt:Title" />
								</text:h>
								<table:table table:name="TabelleRoutines"
									table:style-name="Tabelle1">
									<table:table-column table:style-name="Tabelle1.A" />
									<table:table-column table:style-name="Tabelle1.B" />
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Routine ID</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<xsl:apply-templates select="Routine/@id" />
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Purpose</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<xsl:apply-templates select="Routine/Purpose" />
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Reference</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<text:p text:style-name="Table_20_Contents">
												<xsl:for-each select="Routine/Reference">
													<xsl:apply-templates select="." />
													<xsl:if test="position() != last()">
														<xsl:text>, </xsl:text>
													</xsl:if>
												</xsl:for-each>
											</text:p>
										</table:table-cell>
									</table:table-row>
									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A1"
											office:value-type="string">
											<text:p text:style-name="P8">Parameter</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B1"
											office:value-type="string">
											<xsl:for-each select="Routine/Parameter">
												<text:p text:style-name="Table_20_Contents">
													<xsl:apply-templates select="./@key"/>:	<xsl:apply-templates select="." />
												</text:p>
											</xsl:for-each>
										</table:table-cell>
									</table:table-row>

									<table:table-row>
										<table:table-cell table:style-name="Tabelle1.A2"
											office:value-type="string">
											<text:p text:style-name="P8">Routine steps</text:p>
										</table:table-cell>
										<table:table-cell table:style-name="Tabelle1.B2"
											office:value-type="string">
											<text:list text:style-name="L675">
												<xsl:for-each select="gt:Routine/gt:RoutineStep">
													<text:list-item>
														<!-- Handling of all Action Step -->
														<xsl:call-template name="ActionStep" />
													</text:list-item>
												</xsl:for-each>
											</text:list>
										</table:table-cell>
									</table:table-row>
								</table:table>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>

					<!-- Handling of test layers -->
					<xsl:for-each select="gt:TestLayer">
						<xsl:variable name="filenameLayer" select="." />
						<xsl:for-each select="document($filenameLayer)">
							<!-- Introducing text paragraphs of layer -->
							<xsl:for-each select="gt:TestLayer/gt:Text">
								<xsl:for-each select="*">
									<xsl:if test="name(.)='Paragraph'">
										<text:p text:style-name="Standard">
											<xsl:value-of select="." />
										</text:p>
									</xsl:if>
									<xsl:if test="name(.)='Image'">
										<xsl:variable name="imagePath" select="./@caption" />
										<text:p text:style-name="Standard">
											<draw:frame draw:style-name="fr1" draw:name="Rahmen1"
												text:anchor-type="paragraph" svg:y="0cm" svg:width="17cm"
												draw:z-index="1">
												<draw:text-box fo:min-height="4.165cm">
													<text:p text:style-name="Illustration">
														<draw:frame draw:style-name="fr3" draw:name="Grafik1"
															text:anchor-type="paragraph" svg:x="0.004cm" svg:y="0.002cm"
															svg:width="17cm" style:rel-width="100%" svg:height="4.165cm"
															style:rel-height="scale" draw:z-index="2">
															<draw:image xlink:href="Pictures/TestUnit_M_Overview.JPG"
																xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad" />
														</draw:frame>
														Figure
														<text:sequence text:ref-name="refIllustration0"
															text:name="Illustration" text:formula="ooow:Illustration+1"
															style:num-format="1">1</text:sequence>
														:
														<xsl:value-of select="./@caption" />
													</text:p>
												</draw:text-box>
											</draw:frame>
										</text:p>
									</xsl:if>
									<xsl:if test="name(.)='Heading'">
										<xsl:call-template name="Headline" />
									</xsl:if>
									<xsl:if test="name(.)='Table'">
										<xsl:call-template name="Table" />
									</xsl:if>
								</xsl:for-each>

							</xsl:for-each>

							<xsl:variable name="filenameUnit" select="gt:TestLayer/gt:TestUnit" />
							<xsl:for-each select="document($filenameUnit)">

								<text:h text:style-name="Heading_20_2"
									text:outline-level="2">
									<text:bookmark text:name="__RefHeading__5546_763239732" />
									<xsl:apply-templates select="gt:TestUnit/@id" />
									<text:bookmark-end text:name="__RefHeading__5546_763239732" />
								</text:h>

								<!-- Introducing text paragraphs of unit -->
								<xsl:for-each select="gt:TestUnit/gt:Text">
									<xsl:for-each select="*">
										<xsl:if test="name(.)='Paragraph'">
											<text:p text:style-name="Standard">
												<xsl:value-of select="." />
											</text:p>
										</xsl:if>
										<xsl:if test="name(.)='Image'">
											<xsl:variable name="imagePath" select="./@caption" />
											<text:p text:style-name="Standard">
												<draw:frame draw:style-name="fr1" draw:name="Rahmen1"
													text:anchor-type="paragraph" svg:y="0cm" svg:width="17cm"
													draw:z-index="1">
													<draw:text-box fo:min-height="4.165cm">
														<text:p text:style-name="Illustration">
															<draw:frame draw:style-name="fr3" draw:name="Grafik1"
																text:anchor-type="paragraph" svg:x="0.004cm" svg:y="0.002cm"
																svg:width="17cm" style:rel-width="100%" svg:height="4.165cm"
																style:rel-height="scale" draw:z-index="2">
																<draw:image xlink:href="{imagePath}"
																	xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad" />
															</draw:frame>
															Figure
															<text:sequence text:ref-name="refIllustration0"
																text:name="Illustration" text:formula="ooow:Illustration+1"
																style:num-format="1">1</text:sequence>
															:
															<xsl:value-of select="./@caption" />
														</text:p>
													</draw:text-box>
												</draw:frame>
											</text:p>
										</xsl:if>
										<xsl:if test="name(.)='Heading'">
											<xsl:call-template name="Headline" />
										</xsl:if>
									</xsl:for-each>
								</xsl:for-each>

								<xsl:variable name="filenameTC" select="gt:TestUnit/gt:TestCase" />
								<xsl:for-each select="document($filenameTC)">
									<!-- Test Case -->
									<!-- ID: <xsl:apply-templates select="TestCase/@id"/> -->
									<text:h text:style-name="Heading_20_3"
										text:outline-level="3">
										<xsl:apply-templates select="gt:TestCase/gt:Title" />
									</text:h>

									<!-- Skip test case if comment = "deleted" -->
									<xsl:if test="gt:TestCase/gt:Comment">
										<text:p text:style-name="Standard">
											Test case is deleted:
											<xsl:apply-templates select="gt:TestCase/gt:Comment" />
										</text:p>
									</xsl:if>
									<xsl:if test="not(gt:TestCase/gt:Comment)">

										<table:table table:name="Tabelle1"
											table:style-name="Tabelle1">
											<table:table-column table:style-name="Tabelle1.A" />
											<table:table-column table:style-name="Tabelle1.B" />
											<table:table-row>
												<table:table-cell table:style-name="Tabelle1.A1"
													office:value-type="string">
													<text:p text:style-name="P8">Testcase ID</text:p>
												</table:table-cell>
												<table:table-cell table:style-name="Tabelle1.B1"
													office:value-type="string">
													<text:p text:style-name="Table_20_Contents">
														<xsl:apply-templates select="gt:TestCase/@id" />
													</text:p>
												</table:table-cell>
											</table:table-row>
											<table:table-row>
												<table:table-cell table:style-name="Tabelle1.A2"
													office:value-type="string">
													<text:p text:style-name="P8">Purpose</text:p>
												</table:table-cell>
												<table:table-cell table:style-name="Tabelle1.B2"
													office:value-type="string">
													<text:p text:style-name="Table_20_Contents">
														<xsl:apply-templates select="gt:TestCase/gt:Purpose" />
													</text:p>
												</table:table-cell>
											</table:table-row>
											<table:table-row>
												<table:table-cell table:style-name="Tabelle1.A2"
													office:value-type="string">
													<text:p text:style-name="P8">Version</text:p>
												</table:table-cell>
												<table:table-cell table:style-name="Tabelle1.B2"
													office:value-type="string">
													<text:p text:style-name="Table_20_Contents">
														<xsl:apply-templates select="gt:TestCase/gt:Version" />
													</text:p>
												</table:table-cell>
											</table:table-row>
											<xsl:if test="gt:TestCase/gt:Precondition">
												<table:table-row>
													<table:table-cell table:style-name="Tabelle1.A2"
														office:value-type="string">
														<text:p text:style-name="P8">Pre-conditions</text:p>
													</table:table-cell>
													<table:table-cell table:style-name="Tabelle1.B2"
														office:value-type="string">
														<text:list text:style-name="L675">
															<xsl:for-each select="gt:TestCase/gt:Precondition">
																<text:list-item>
																	<!-- Handling of all Action Step -->
																	<xsl:call-template name="ActionStep" />
																</text:list-item>

															</xsl:for-each>
														</text:list>

													</table:table-cell>
												</table:table-row>
											</xsl:if>
											<xsl:if test="gt:TestCase/gt:TestStep">
												<table:table-row>
													<table:table-cell table:style-name="Tabelle1.A2"
														office:value-type="string">
														<text:p text:style-name="P8">Test scenario</text:p>
													</table:table-cell>
													<table:table-cell table:style-name="Tabelle1.B2"
														office:value-type="string">
														<text:list text:style-name="L675">
															<xsl:for-each select="gt:TestCase/gt:TestStep">
																<text:list-item>
																	<!-- Handling of all Action Step -->
																	<xsl:call-template name="ActionStep" />
																</text:list-item>
															</xsl:for-each>
														</text:list>

													</table:table-cell>
												</table:table-row>
											</xsl:if>
											<xsl:if test="gt:TestCase/gt:Postcondition">
												<table:table-row>
													<table:table-cell table:style-name="Tabelle1.A2"
														office:value-type="string">
														<text:p text:style-name="P8">Post-conditions</text:p>
													</table:table-cell>
													<table:table-cell table:style-name="Tabelle1.B2"
														office:value-type="string">
														<text:list text:style-name="L675">
															<xsl:for-each select="gt:TestCase/gt:Postcondition">
																<text:list-item>
																	<!-- Handling of all Action Step -->
																	<xsl:call-template name="ActionStep" />
																</text:list-item>

															</xsl:for-each>
														</text:list>

													</table:table-cell>
												</table:table-row>
											</xsl:if>
										</table:table>


									</xsl:if>
								</xsl:for-each>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>	<!-- End of test layer -->


					<!-- Revision history -->
					<text:p text:style-name="Standard" />
					<text:h text:style-name="Heading_20_2" text:outline-level="2"
						text:is-list-header="true">Revision History</text:h>
					<table:table table:name="Tabelle25" table:style-name="Tabelle25">
						<table:table-column table:style-name="Tabelle25.A" />
						<table:table-column table:style-name="Tabelle25.B" />
						<table:table-column table:style-name="Tabelle25.C" />
						<table:table-column table:style-name="Tabelle25.D" />
						<table:table-row>
							<table:table-cell table:style-name="Tabelle25.A1"
								office:value-type="string">
								<text:p text:style-name="P8">Version</text:p>
							</table:table-cell>
							<table:table-cell table:style-name="Tabelle25.A1"
								office:value-type="string">
								<text:p text:style-name="P8">Date</text:p>
							</table:table-cell>
							<table:table-cell table:style-name="Tabelle25.A1"
								office:value-type="string">
								<text:p text:style-name="P8">Alteration</text:p>
							</table:table-cell>
							<table:table-cell table:style-name="Tabelle25.D1"
								office:value-type="string">
								<text:p text:style-name="P8">Author</text:p>
							</table:table-cell>
						</table:table-row>

						<xsl:for-each select="gt:VersionHistory/gt:Version">
							<table:table-row>
								<table:table-cell table:style-name="Tabelle25.A2"
									office:value-type="string">
									<text:p text:style-name="Table_20_Contents">
										<xsl:apply-templates select="gt:VersionID" />
									</text:p>
								</table:table-cell>
								<table:table-cell table:style-name="Tabelle25.A2"
									office:value-type="string">
									<text:p text:style-name="Table_20_Contents">
										<xsl:apply-templates select="gt:Date" />
									</text:p>
								</table:table-cell>
								<table:table-cell table:style-name="Tabelle25.A2"
									office:value-type="string">
									<text:p text:style-name="Table_20_Contents">
										<xsl:apply-templates select="gt:Description" />
									</text:p>
								</table:table-cell>
								<table:table-cell table:style-name="Tabelle25.D2"
									office:value-type="string">
									<text:p text:style-name="Table_20_Contents">
										<xsl:apply-templates select="gt:Editor" />
									</text:p>
								</table:table-cell>
							</table:table-row>
						</xsl:for-each>

					</table:table>


				</office:text>
			</office:body>

		</office:document-content>

	</xsl:template>

	<!-- Use this template to handle all three kinds of headlines -->
	<xsl:template name="Headline">
		<xsl:if test=".='Introduction'">
			<!-- Use this type of heading to activate naming of paragraphs in headers -->
			<text:h text:style-name="P15" text:outline-level="1"
				text:restart-numbering="true" text:start-value="-1">
				<text:bookmark text:name="__RefHeading__5546_763239732" />
				<xsl:apply-templates select="." />
				<text:bookmark-end text:name="__RefHeading__5546_763239732" />
			</text:h>
		</xsl:if>
		<xsl:if test="(./@level='1') and not (.='Introduction')">
			<text:h text:style-name="Heading_20_1" text:outline-level="1">
				<text:bookmark text:name="__RefHeading__5546_763239732" />
				<xsl:apply-templates select="." />
				<text:bookmark-end text:name="__RefHeading__5546_763239732" />
			</text:h>
		</xsl:if>
		<xsl:if test="./@level='2'">
			<text:h text:style-name="Heading_20_2" text:outline-level="2">
				<text:bookmark text:name="__RefHeading__5546_763239732" />
				<xsl:apply-templates select="." />
				<text:bookmark-end text:name="__RefHeading__5546_763239732" />
			</text:h>
		</xsl:if>
		<xsl:if test="./@level='3'">
			<text:h text:style-name="Heading_20_3" text:outline-level="3">
				<xsl:apply-templates select="." />
			</text:h>
		</xsl:if>
	</xsl:template>


	<!-- Use this template to handle tables -->
	<xsl:template name="Table">
		<!-- Header for table -->
		<text:p text:style-name="Standard">
		</text:p>
		<text:p text:style-name="Standard">
			<xsl:value-of select="./@caption" />
			:
		</text:p>

		<xsl:variable name="numberOfRows" select="count(current()//Row)" />
		<xsl:variable name="numberOfCells" select="count(current()//Cell)" />
		<xsl:variable name="numberOfColumns" select="$numberOfCells div $numberOfRows " />

		<!-- Constructing basic table -->
		<table:table table:name="TabelleTEST">
			<!-- ToDo: Replace this smelly code! -->
			<xsl:if test="$numberOfColumns='2'">
				<table:table-column />
				<table:table-column />
			</xsl:if>
			<xsl:if test="$numberOfColumns='3'">
				<table:table-column />
				<table:table-column />
				<table:table-column />
			</xsl:if>
			<xsl:if test="$numberOfColumns='4'">
				<table:table-column />
				<table:table-column />
				<table:table-column />
				<table:table-column />
			</xsl:if>

			<xsl:for-each select="Row">
				<table:table-row>
					<xsl:for-each select="Cell">
						<table:table-cell>
							<text:p>
								<xsl:value-of select="." />
							</text:p>
						</table:table-cell>
					</xsl:for-each>
				</table:table-row>
			</xsl:for-each>
		</table:table>
	</xsl:template>

	<!-- Use this template to handle ActionSteps -->

	<xsl:template name="ActionStep">

		<!-- ActionStep Command -->
		<xsl:if test="boolean(gt:Command)">
			<text:p text:style-name="P18">
				<xsl:apply-templates select="gt:Command/gt:Text" />
			</text:p>
			<xsl:if test="boolean(gt:Command/gt:APDU)">
				<text:p text:style-name="P689">
					<xsl:apply-templates select="gt:Command/gt:APDU" />
				</text:p>
				<xsl:if test="gt:Command/gt:APDU/@sm='true'">
					<text:p text:style-name="P18"> within SM.</text:p>
				</xsl:if>
			</xsl:if>
			<xsl:for-each select="gt:Description">
				<text:p text:style-name="P18">
					<xsl:apply-templates select="." />
				</text:p>
			</xsl:for-each>
		</xsl:if>
		<!-- ActionStep RoutineCall -->
		<xsl:if test="boolean(gt:RoutineCall)">
			ROUTINE: perform
			<xsl:apply-templates select="gt:RoutineCall/@target" />
			(
			<xsl:for-each select="gt:RoutineCall/gt:Parameter">
				<xsl:apply-templates select="." />
				<xsl:if test="position() != last()">
					<xsl:text>, </xsl:text>
				</xsl:if>
			</xsl:for-each>
			)
		</xsl:if>

		<!-- Expected Result -->
		<xsl:for-each select="gt:ExpectedResult">
			<xsl:if test="string-length(Text)>=1">
				<text:p text:style-name="P18">
					Expected Result:
					<xsl:apply-templates select="gt:Text" />
				</text:p>
			</xsl:if>
			<xsl:if test="gt:APDU">
				<text:p text:style-name="P18">
					Expected R-APDU: '
					<xsl:apply-templates select="gt:APDU" />
					'
					<xsl:if test="gt:APDU/@sm='true'">
						within a valid SM response.
					</xsl:if>
				</text:p>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>


</xsl:stylesheet> 



 