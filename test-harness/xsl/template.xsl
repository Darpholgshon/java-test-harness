<?xml version="1.0" ?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" encoding="ISO-8859-1" indent="yes" /> 
<xsl:template match="body.content">
<html>
<head>
	<title>Example</title> 
</head>

<body> 
	 	<xsl:value-of select="."/>
</body>

</html>
</xsl:template>
</xsl:stylesheet>
