Attribute VB_Name = "NewMacros"
Option Explicit

Public Type procedure
    id As String
    step As String
    requirements() As String
End Type

Public Type header
    test_case_id As String
    test_case_title As String
    tester As String
    sw_version As String
    procedures() As procedure
End Type

Public excel_data() As header
Dim ids_list() As String
Dim procedures_list() As String

Function get_row_count(rng As range) As Long
    Application.Volatile
    Dim row_index As String
    Dim cell_address_array() As String
    row_index = rng.Parent.cells(Rows.count, rng.Column).End(xlUp).Address(rowabsolute:=True)
    cell_address_array = Split(row_index, "$")
    get_row_count = cell_address_array(2)

End Function

Function in_list(list() As String, val As String)

    Dim v As Variant

    For Each v In list
        If val = v Then
            in_list = True
            Exit For
        Else
            in_list = False
        End If
    Next v

End Function

Sub add_to_list(list() As String, val As String)

    If Not list(0) = "" Then
        ReDim Preserve list(UBound(list) + 1)
    End If
    list(UBound(list)) = val
    
End Sub

Sub read_data()

    Dim XLSheet As Object
    Dim data_range As range
    Dim r As Long
    Dim c As Long
    Dim rows_in_data As Long
    Dim t_count As Long
    Dim p_count As Long
    Dim value As String
    
    Set XLSheet = worksheets("Data")
    Set data_range = XLSheet.range("data_info")
    rows_in_data = get_row_count(data_range)
    
    ReDim excel_data(0)
    ReDim ids_list(0)
    ReDim procedures_list(0)
    
    t_count = -1
    p_count = -1
    
    For r = 2 To rows_in_data
        For c = 1 To 7
            value = XLSheet.cells(r, c)
            If c = 1 Then
            
                If Not in_list(ids_list, value) Then
                    Call add_to_list(ids_list, value)
                    t_count = t_count + 1
                    ReDim Preserve excel_data(t_count)
                    excel_data(t_count).test_case_id = value
                    p_count = -1
                End If
                
            ElseIf c = 2 Then
                excel_data(t_count).test_case_title = value
            ElseIf c = 3 Then
                excel_data(t_count).tester = value
            ElseIf c = 4 Then
                excel_data(t_count).sw_version = value
            ElseIf c = 5 Then
            
                If Not in_list(procedures_list, value) Then
                    Call add_to_list(procedures_list, value)
                    p_count = p_count + 1
                    ReDim Preserve excel_data(t_count).procedures(p_count)
                    excel_data(t_count).procedures(p_count).id = value
                End If
                
            ElseIf c = 6 Then
                    excel_data(t_count).procedures(p_count).step = value
                
            ElseIf c = 7 Then
                excel_data(t_count).procedures(p_count).requirements = Split(value, ",")
            End If
        Next c
    Next r
    
    Set XLSheet = Nothing
    
End Sub

Sub set_margins(doc As Object)
'
' set_margins Macro
' Macro recorded 9/1/2004 by cbh
'
    With doc.Styles(wdStyleNormal).Font
        If .NameFarEast = .NameAscii Then
            .NameAscii = ""
        End If
        .NameFarEast = ""
    End With
    With doc.PageSetup
        .LineNumbering.Active = False
        .Orientation = wdOrientPortrait
        .TopMargin = InchesToPoints(1.5)
        .BottomMargin = InchesToPoints(1.25)
        .LeftMargin = InchesToPoints(1)
        .RightMargin = InchesToPoints(1)
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
        .TwoPagesOnOne = False
        .BookFoldPrinting = False
        .BookFoldRevPrinting = False
        .BookFoldPrintingSheets = 1
        .GutterPos = wdGutterPosLeft
    End With
    
End Sub

Sub create_header(doc As Object)

    doc.Sections(1).Headers(wdHeaderFooterPrimary).Shapes.AddPicture _
        filename:=ActiveWorkbook.Path & "\eagle.png", _
        linktofile:=True, _
        savewithdocument:=True, _
        Left:=True, _
        Top:=True, _
        Width:=70, _
        Height:=70
        
    With doc.Sections(1).Headers(wdHeaderFooterPrimary)
        With .Shapes(1)
            .PictureFormat.TransparentBackground = msoTrue
            .PictureFormat.TransparencyColor = RGB(0, 153, 0)
            .Fill.Visible = msoFalse
        End With
        With .range
            .ParagraphFormat.Alignment = wdAlignParagraphCenter
            .Font.Bold = True
            .Font.Size = 14
            .Font.Name = "Arial"
            .InsertAfter "Raytheon Ft Wayne DD(X)" & vbCrLf & "Test Case Run Log"
        End With
    End With
    
End Sub

Sub create_table(doc As Object)

    Dim myrange As Word.range
    
    Set myrange = doc.range
    myrange.Collapse Direction:=wdCollapseEnd
    
    doc.Tables.Add _
            range:=myrange, _
            numrows:=27, _
            numcolumns:=1, _
            DefaultTableBehavior:=wdWord9TableBehavior

End Sub

Sub insert_text(myrange As Word.range, str As String)

    With myrange
        .InsertAfter str
        .Font.Bold = False
        .Font.Size = 12
        .Font.Name = "Arial"
    End With

End Sub

Sub split_row(myrange As Word.range, cell_count As Integer)

    If cell_count > 0 Then
        myrange.cells.Split numrows:=1, numcolumns:=cell_count, mergebeforesplit:=True
    End If
    
End Sub

Sub update_status_bar(index As Long, total As Long)

    Application.StatusBar = "Processing ... Sheet " & index & " of " & total

End Sub

Sub create_sheet(doc As Object, data() As NewMacros.header)

    Dim i As Long
    Dim myrange As Word.range
    Dim last_table As Long
    Dim row As Long

    Call set_margins(doc)
    Call create_header(doc)
    
    For i = LBound(data) To UBound(data)
'    For i = LBound(data) To 2
    
        Call create_table(doc)
        last_table = doc.Tables.count
        Set myrange = doc.Tables(last_table).range
        myrange.Rows.Height = 20
        
        Set myrange = doc.Tables(last_table).cell(1, 1).range
        
        Call insert_text(myrange, "Test Case ID: ")
        Call insert_text(myrange, data(i).test_case_id)
        
        Set myrange = doc.Tables(last_table).cell(2, 1).range
        Call insert_text(myrange, "Test Case Title: ")
        Call insert_text(myrange, data(i).test_case_title)
        
        Set myrange = doc.Tables(last_table).cell(3, 1).range
        Call insert_text(myrange, "Test Operator: ")
        Call insert_text(myrange, data(i).tester)
        
        Set myrange = doc.Tables(last_table).cell(4, 1).range
        Call split_row(myrange, 2)
        Set myrange = doc.Tables(last_table).cell(4, 2).range
        Call insert_text(myrange, "SQE Witness: ")
        
        Set myrange = doc.Tables(last_table).cell(5, 1).range
        Call split_row(myrange, 2)
        Set myrange = doc.Tables(last_table).cell(5, 2).range
        Call insert_text(myrange, "IV & V Witness: ")
        
        Set myrange = doc.Tables(last_table).cell(6, 1).range
        Call split_row(myrange, 2)
        Set myrange = doc.Tables(last_table).cell(6, 2).range
        Call insert_text(myrange, "Safety Witness: ")
        
        Set myrange = doc.Tables(last_table).cell(7, 1).range
        Call split_row(myrange, 3)
        Call insert_text(myrange, "Date: ")
        
        Set myrange = doc.Tables(last_table).cell(7, 2).range
        Call insert_text(myrange, "Start Time: ")
        
        Set myrange = doc.Tables(last_table).cell(7, 3).range
        Call insert_text(myrange, "Stop Time: ")
        
        Set myrange = doc.Tables(last_table).cell(8, 1).range
        Call split_row(myrange, 2)
        Call insert_text(myrange, "SW Version: ")
        Call insert_text(myrange, data(i).sw_version)
        
        Set myrange = doc.Tables(last_table).cell(8, 2).range
        Call insert_text(myrange, "Pass:" & vbTab & vbTab & vbTab & "Fail:")
        
        Set myrange = doc.Tables(last_table).cell(9, 1).range
        Call split_row(myrange, 4)
        
        Call insert_text(myrange, "Procedure ID")
        myrange.FormattedText.ParagraphFormat.Alignment = wdAlignParagraphCenter
        
        Set myrange = doc.Tables(last_table).cell(9, 2).range
        Call insert_text(myrange, "Step")
        myrange.FormattedText.ParagraphFormat.Alignment = wdAlignParagraphCenter
        
        Set myrange = doc.Tables(last_table).cell(9, 3).range
        Call insert_text(myrange, "Requirement")
        myrange.FormattedText.ParagraphFormat.Alignment = wdAlignParagraphCenter
        
        Set myrange = doc.Tables(last_table).cell(9, 4).range
        Call insert_text(myrange, "Comments")
        myrange.FormattedText.ParagraphFormat.Alignment = wdAlignParagraphCenter
        
        For row = 10 To 27
            Set myrange = doc.Tables(last_table).cell(row, 1).range
            Call split_row(myrange, 4)
        Next row
        
        Set myrange = doc.range
        myrange.Collapse Direction:=wdCollapseEnd
        myrange.InsertBreak Type:=wdPageBreak
        
        Call update_status_bar((i + 1), UBound(data))
        
    Next i
    
    Application.StatusBar = ""
    
End Sub

Sub create_sheets()
    
    On Error GoTo handler

    Dim wordapp As Word.Application
    Dim worddoc As Word.Document
    Dim filename As String
    filename = Application.ActiveWorkbook.Path & "\data_sheets_2.doc"
    
    Set wordapp = New Word.Application
    Set worddoc = wordapp.Documents.Add
'    wordapp.Visible = True
    
    read_data
    Call create_sheet(worddoc, excel_data)
    
    worddoc.SaveAs (filename)
    worddoc.Close True
    wordapp.Quit
    
    MsgBox "Data sheets saved as " & filename
    
handler:

    If Not IsNull(worddoc) Then
        Set worddoc = Nothing
    End If
            
    If Not IsNull(wordapp) Then
        Set wordapp = Nothing
    End If

End Sub

