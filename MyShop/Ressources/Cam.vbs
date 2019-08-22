Set objShell = CreateObject("WScript.Shell")
	Dim cur
	cur = "Succes"
	
	ExcelMacroExample

Sub ExcelMacroExample() 

	Dim xlApp 
	Dim xlBook 
	Dim xlsFile

	xlsFile = Left(Wscript.ScriptFullName, Len(Wscript.ScriptFullName) - Len(Wscript.ScriptName)) & "testScr.xls"
	'MsgBox(xlsFile)
	Set xlApp = CreateObject("Excel.Application") 
	Set xlBook = xlApp.Workbooks.Open(xlsFile) 
	xlApp.Run "exporterModule"
	DisplayAlerts = False
	xlApp.Quit 
	
End Sub 
