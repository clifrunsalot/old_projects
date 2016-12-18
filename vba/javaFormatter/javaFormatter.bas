Attribute VB_Name = "NewMacros"
Option Explicit
Option Base 1

Sub format_diffs()
Attribute format_diffs.VB_Description = "Macro recorded 08/11/03 by PC Pool"
Attribute format_diffs.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.format_diffs"
'
' format_diffs Macro
' Macro recorded 08/11/03 by PC Pool
'
    Selection.HomeKey Unit:=wdStory
    With ActiveDocument.PageSetup
        .LineNumbering.Active = False
        .Orientation = wdOrientPortrait
        .TopMargin = InchesToPoints(1)
        .BottomMargin = InchesToPoints(1)
        .LeftMargin = InchesToPoints(0.5)
        .RightMargin = InchesToPoints(0.5)
        .Gutter = InchesToPoints(0)
        .HeaderDistance = InchesToPoints(0.5)
        .FooterDistance = InchesToPoints(0.5)
        .PageWidth = InchesToPoints(8.5)
        .PageHeight = InchesToPoints(11)
        .FirstPageTray = wdPrinterDefaultBin
        .OtherPagesTray = wdPrinterDefaultBin
        .SectionStart = wdSectionNewPage
        .OddAndEvenPagesHeaderFooter = False
        .DifferentFirstPageHeaderFooter = False
        .VerticalAlignment = wdAlignVerticalTop
        .SuppressEndnotes = False
        .MirrorMargins = False
    End With
    Selection.WholeStory
    Selection.Style = ActiveDocument.Styles("diff_format_to_courier_new")
    Selection.HomeKey Unit:=wdStory
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    Selection.Find.Replacement.Style = ActiveDocument.Styles("diff_new")
    Selection.Find.Replacement.Highlight = False
    With Selection.Find.Replacement.Font
        .Name = "Courier New"
        .Size = 9
        .Bold = False
        .Italic = False
        .ColorIndex = wdRed
    End With
    With Selection.Find
        .Text = "+++"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindContinue
        .Format = True
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute Replace:=wdReplaceAll
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    Selection.Find.Replacement.Style = ActiveDocument.Styles("diff_new")
    Selection.Find.Replacement.Highlight = False
    With Selection.Find.Replacement.Font
        .Size = 9
        .Bold = False
        .Italic = False
        .ColorIndex = wdRed
    End With
    With Selection.Find
        .Text = " +|"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindContinue
        .Format = True
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute Replace:=wdReplaceAll
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    Selection.Find.Replacement.Style = ActiveDocument.Styles("diff_old")
    Selection.Find.Replacement.Highlight = False
    With Selection.Find.Replacement.Font
        .Name = "Courier New"
        .Size = 9
        .Bold = False
        .Italic = False
        .ColorIndex = wdBlue
    End With
    With Selection.Find
        .Text = "---"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindContinue
        .Format = True
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute Replace:=wdReplaceAll
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    Selection.Find.Replacement.Style = ActiveDocument.Styles("diff_old")
    Selection.Find.Replacement.Highlight = False
    With Selection.Find.Replacement.Font
        .Name = "Courier New"
        .Size = 9
        .Bold = False
        .Italic = False
        .ColorIndex = wdBlue
    End With
    With Selection.Find
        .Text = " -|"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindContinue
        .Format = True
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute Replace:=wdReplaceAll
End Sub
Sub test1()
Attribute test1.VB_Description = "Macro recorded 05/21/04 by PC Pool"
Attribute test1.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.test1"
'
' test1 Macro
' Macro recorded 05/21/04 by PC Pool
'
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    With Selection.Find
        .Text = "/**"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindContinue
        .Format = False
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute
    Selection.Font.ColorIndex = wdBrightGreen
    Selection.Find.ClearFormatting
    With Selection.Find
        .Text = " *"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindAsk
        .Format = False
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute
    Selection.EndKey Unit:=wdLine, Extend:=wdExtend
    Selection.Font.ColorIndex = wdBrightGreen
    Selection.Find.ClearFormatting
    With Selection.Find
        .Text = " *"
        .Replacement.Text = ""
        .Forward = True
        .Wrap = wdFindAsk
        .Format = False
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute
    Selection.Find.Execute
    Selection.EndKey Unit:=wdLine, Extend:=wdExtend
    Selection.Font.ColorIndex = wdBrightGreen
End Sub

Function getKeyWords()

'
' Returns an array of Java keywords
'
    getKeyWords = Array("abstract", "assert", "boolean", "break", _
                            "byte", "case", "catch", "char", _
                            "class", "const", "continue", "default", _
                            "do", "double", "else", "extends", _
                            "false", "final", "finally", "float", _
                            "for", "goto", "if", "implements", _
                            "import", "instanceof", "int", "interface", _
                            "long", "native", "new", "null", _
                            "package", "private", "protected", "public", _
                            "return", "short", "static", "strictfp", _
                            "super", "switch", "synchronized", "this", _
                            "threadsafe", "throw", "throws", "transient", _
                            "true", "try", "void", "volatile", "while")
                            
 End Function

Sub formatCmts()

'
' Formats comments to bold and the color to green
'
    Const beginMark As String = "/**"
    Const middleMark As String = "*"
    Const endMark As String = "*/"
    Const oneLineCmt As String = "//"
    
    Dim strLen As Integer
    Dim startPosn As Integer
    Dim str As String
    Dim index As Long
    Dim lines As Integer
    Dim test As String
    lines = ActiveDocument.Sentences.count
        
    For index = 1 To lines
    
        str = ActiveDocument.Sentences(index)
        strLen = Len(str)
        
        If InStr(1, str, beginMark, vbTextCompare) > 0 Or _
            InStr(1, str, middleMark, vbTextCompare) > 0 Or _
            InStr(1, str, endMark, vbTextCompare) > 0 Or _
            InStr(1, str, oneLineCmt, vbTextCompare) > 0 Then
            
            If InStr(1, str, beginMark, vbTextCompare) > 0 Or _
                InStr(1, str, endMark, vbTextCompare) > 0 Or _
                InStr(1, str, oneLineCmt, vbTextCompare) > 0 Then
                
                GoTo formatIt
            
            ElseIf InStr(1, str, middleMark, vbTextCompare) > 0 Then

                startPosn = InStr(1, str, middleMark, vbTextCompare)
                
                If startPosn > 0 Then
                
                    test = Trim(Mid(str, 1, startPosn - 1))
                    If test = "" Then
                        GoTo formatIt
                    Else
                        GoTo skipIt
                    End If
                    
                End If
                
            End If
            
formatIt:
            With ActiveDocument.Sentences(index)
                .Bold = True
                .Font.ColorIndex = wdGreen
            End With
                
skipIt:
                
                ' This allows user to do other things while this is processing;
                ' otherwise, nothing would happen until this sub is done.
                DoEvents
            
        End If
    
    Next index
End Sub

Sub formatKeyWords()

'
' Formats Java keywords to bold and the color of blue
'
    Dim str As String
    Dim lines As Integer
    Dim w As Variant
    Dim keyWordArray() As String
    Dim count As Integer
    Dim wIndex As Integer
    Dim index As Integer
    Dim keyword As Variant
    Dim rng As Variant
    wIndex = 1
    lines = ActiveDocument.Sentences.count
    
    count = UBound(getKeyWords)
    ReDim keyWordArray(count)
    For Each w In getKeyWords
        keyWordArray(wIndex) = w
        wIndex = wIndex + 1
    Next w
    
    For index = 1 To lines
    
        str = ActiveDocument.Sentences(index)
        
        For wIndex = 1 To ActiveDocument.Sentences(index).words.count
        
            w = Trim(ActiveDocument.Sentences(index).words(wIndex))
            
            For Each keyword In keyWordArray
            
                If w = keyword Then
                                
                    Set rng = ActiveDocument.Sentences(index).words(wIndex)
                    
                    With rng
                        .Bold = True
                        .Font.ColorIndex = wdBlue
                    End With
                
                    DoEvents
                    
                End If
            
            Next keyword
            
        Next wIndex
    
    Next index

End Sub


Sub formatKeyWords2()

'
' Formats Java keywords to bold and the color of blue
'
    
    '****************************
    ' Populate local keyWordArray
    '****************************
    Dim kwCount As Long
    kwCount = UBound(getKeyWords)
    Dim keyWordArray() As String
    ReDim keyWordArray(kwCount)
    Dim wIndex As Long
    wIndex = 1
    Dim w As Variant
    '
    For Each w In getKeyWords
        keyWordArray(wIndex) = w
        wIndex = wIndex + 1
    Next w
    
    '****************************
    ' Format keywords
    '****************************
    Dim index As Long
    Dim kwIndex As Long
    Dim cword As String
    Dim keyword As String
    Dim wCount As Long
    Dim startPosn As Long
    Dim rng As Variant
    wCount = ActiveDocument.words.count
    '
    For index = 1 To wCount
        cword = Trim(ActiveDocument.words(index).Text)
        
        For kwIndex = 1 To kwCount
            If cword = keyWordArray(kwIndex) Then
    
                Set rng = ActiveDocument.words(index)
                With rng
                    .Bold = True
                    .Font.ColorIndex = wdBlue
                End With
                DoEvents
    
            End If
            DoEvents
            
         Next kwIndex
         DoEvents
        
    Next index


End Sub


Sub formatJavafile()
'
' main program to color code a java file inside Word
'

    formatKeyWords2
    formatCmts

End Sub

