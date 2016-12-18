Sub merge_cells()
'
' merge_cells Macro
' Macro recorded 8/1/2007 by cbhuds
'
    'Selection.MoveRight Unit:=wdCharacter, Count:=3, Extend:=wdExtend
    Selection.Cells.Merge
End Sub
Sub insert_row()
'
' insert_row Macro
' Macro recorded 8/1/2007 by cbhuds
'
    Selection.InsertRowsBelow 1
End Sub
Sub paint_red()
'
' paint_red Macro
' Macro recorded 8/1/2007 by cbhuds
'
    Selection.Font.Color = wdColorRed
    Selection.Font.Bold = wdToggle
    Selection.Font.Bold = wdToggle
End Sub
Sub Macro1()
'
' Macro1 Macro
' Macro recorded 8/1/2007 by cbhuds
'
    Selection.Font.Color = wdColorRed
    Selection.Font.Bold = wdToggle
End Sub
Sub center_text()
'
' center_text Macro
' Macro recorded 8/1/2007 by cbhuds
'
    Selection.ParagraphFormat.Alignment = wdAlignParagraphCenter
End Sub
Sub delete_row()
'
' delete_row Macro
' Macro recorded 8/7/2007 by cbhuds
'
    Selection.SelectRow
    Selection.Rows.Delete
End Sub
Sub replace_hyperlinks(old_link As String, new_link As String)

    Dim my_doc As Document
    Set my_doc = ActiveDocument
    
    Dim lines As Integer
    lines = my_doc.Hyperlinks.Count
    
    Dim link As Hyperlink
    Dim updated_link As Hyperlink
    
    For Each link In my_doc.Hyperlinks
        If InStr(1, link.Name, old_link, vbTextCompare) <> 0 Then
            updated_name = replace(link.Name, old_link, new_link)
            Dim update_link As Hyperlink
            link.Address = updated_name
        End If
    Next link

End Sub

Function replace(txt As String, old_link As String, new_link As String) As String

    Dim rplct_len As Integer
    rplct_len = Len(old_link)
    
    Dim start_pt As Integer
    start_pt = InStr(1, txt, old_link, vbTextCompare) - 1
    
    Dim start_from_end As Integer
    start_from_end = InStr(1, StrReverse(txt), StrReverse(old_link), vbTextCompare) - 1
    
    Dim before As String
    Dim after As String
    
    before = Left(txt, start_pt)
    after = Right(txt, start_from_end)
    
    replace = before + new_link + after
    
End Function

Sub invoke()

'    Const old As String = "https://www.fcsace.com/Windchill/servlet/WindchillAuthGW/wt.content.ContentHttp/viewContent/"
'    Const new_ As String = "."
    
    Call replace_hyperlinks(Trim(UserForm1.TextBox1), Trim(UserForm1.TextBox2))
    
End Sub

Sub replace_links()

    Call UserForm1.Show
    
End Sub

