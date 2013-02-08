	function writeObject()
	{
		document.write("Launch Softpaint");
		document.write("<OBJECT CODEBASE='http://java.sun.com/products/plugin/autodl/jinstall-1_4_1-windows-i586.cab'"); 
		document.write("CLASSID='clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284' HEIGHT=0 WIDTH=0 ID='WSO'>");
		document.write("<PARAM NAME='app' VALUE='" + getPage() + "'>");
		document.write("<PARAM NAME='back' VALUE='true'>");
		document.write("<!-- Alternate HTML for browsers which cannot instantiate the object -->");
		document.write("<A HREF='http://java.sun.com/j2se/1.4.2/download.html'>");
		document.write("Download Java Web Start</A>");
		document.write("</OBJECT>");		
	}

	function getPage()
	{
		var codebase = document.URL;
		codebase = codebase.substring( 0, codebase.length - 13) + 'testharness.jnlp';
		return codebase;
	}