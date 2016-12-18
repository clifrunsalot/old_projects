Attribute VB_Name = "NewMacros"
Option Explicit

Sub cnvt()
Attribute cnvt.VB_Description = "Macro recorded 2/17/2009 by cbhuds"
Attribute cnvt.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.cnvt"
'
' cnvt Macro
' Macro recorded 2/17/2009 by cbhuds
'
    Selection.Tables(1).Select
    Selection.Rows.ConvertToText Separator:=wdSeparateByCommas, NestedTables:= _
        True
    Selection.Find.ClearFormatting
    Selection.Find.Replacement.ClearFormatting
    With Selection.Find
        .Text = "^p"
        .Replacement.Text = ","
        .Forward = True
        .Wrap = wdFindAsk
        .Format = False
        .MatchCase = False
        .MatchWholeWord = False
        .MatchWildcards = False
        .MatchSoundsLike = False
        .MatchAllWordForms = False
    End With
    Selection.Find.Execute Replace:=wdReplaceAll
    Selection.EndKey Unit:=wdLine
End Sub
