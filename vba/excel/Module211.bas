Attribute VB_Name = "Module2"
Public searchStr As String
Public cellColor As String
Public caseSensitive As String
Public wholeCell As String
Public copyToSheet As String
Public spreadsheetWithData As String
Public last_col As Integer


Sub find_and_replace_text(oldStr As String, newStr As String)

    Dim tmp As String
    Dim last_col As Long
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    
    For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
        If InStr(1, c.Value, oldStr, vbTextCompare) <> 0 Then
'            Debug.Print c.Value
            tmp = Replace(c.Value, oldStr, newStr, , , vbTextCompare)
            c.Value = tmp
        End If
    Next c

End Sub

Sub find_and_copy_orig(tmpStr As String)

    Dim tmp As String
    Dim last_col As Long
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    Dim row As Integer
    Dim col As Integer
    
    For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
        If InStr(1, c.Value, tmpStr, vbTextCompare) <> 0 Then
            row = c.row
            col = c.Column
            c.Select
            Rows(ActiveWindow.RangeSelection.row).Select
            Selection.Copy
            last_row_in_paste_wksht = Val(ActiveWorkbook.Sheets("Sheet2").Cells.SpecialCells(xlLastCell).row)
            row = Str(last_row_in_paste_wksht)
            rowStr = Trim(Str(row + 1))
            rg = "A" + rowStr
            Worksheets("Sheet2").Select
            Range(rg).Select
            Rows(ActiveWindow.RangeSelection.row).Select
            ActiveWorkbook.Sheets("Sheet2").Paste
            Application.CutCopyMode = False
            ActiveWorkbook.Sheets("Sheet1").Select
        End If
    Next c

End Sub


Sub find_and_copy(sht As String, fromRange As Range)

    last_row_in_paste_wksht = Val(ActiveWorkbook.Sheets(sht).Cells.SpecialCells(xlLastCell).row)
    row = Int(Trim(Str(last_row_in_paste_wksht))) + 1
    
    Dim col As Integer
    col = 1
    For Each d In fromRange
    
        Sheets(sht).Cells(row, col).Value = d.Value
        col = col + 1
        
    Next d
    
    Application.CutCopyMode = False
    
End Sub

Sub findit()
    Dim oldStr As String
    Dim newStr As String
    oldStr = Chr(10)
    newStr = "; "
    Call find_and_replace_text(oldStr, newStr)
'    oldStr = Chr(10) & "Check database"
'    newStr = ";Check database"
'    Call find_and_replace_text(oldStr, newStr)
    
End Sub

Sub chgCellsToText()

    Dim last_col As Long
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    
    For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
        oldStr = c.Value
        newStr = "'" & oldStr
        tmp = Replace(c.Value, oldStr, newStr, , , vbTextCompare)
        c.Value = tmp
    Next c


End Sub


Sub fitcol()
'
' Formats columns to fit text
'
    Dim colIdx As Integer
    Dim rowIdx As Integer
    
    ' Set range equal to activesheet
    Dim rg As Range
    Set rg = Application.ActiveSheet.Cells
    ' Get last col and last row indexes
    colIdx = Cells.Find(What:="*", After:=[A1], SearchDirection:=xlPrevious).Column
    rowIdx = Cells.Find(What:="*", After:=[A1], SearchDirection:=xlPrevious).row
    
    ' Cycle thru each column and adjust
    For c = 1 To colIdx
        rg.Cells(1, c).EntireColumn.Select
        With Selection
            .Columns.AutoFit
        End With
    Next c
    
    ' Select first cell
    rg.Cells(1, 1).Select
    
End Sub
Sub fitrow()
'
' Formats rows to fit text
'
    Dim colIdx As Integer
    Dim rowIdx As Integer
    
    ' Set range equal to activesheet
    Dim rg As Range
    Set rg = Application.ActiveSheet.Cells
    ' Get last col and last row indexes
    colIdx = Cells.Find(What:="*", After:=[A1], SearchDirection:=xlPrevious).Column
    rowIdx = Cells.Find(What:="*", After:=[A1], SearchDirection:=xlPrevious).row
    
    ' Cycle thru each row and adjust
    For r = 1 To rowIdx
        rg.Cells(r, 1).EntireRow.Select
        With Selection
            .Rows.AutoFit
        End With
    Next r
    
    ' Select first cell
    rg.Cells(1, 1).Select
    
End Sub
Sub fitcolrow()
'
' Autofits cols and rows
'
    Call fitcol
    Call fitrow
End Sub

Sub yellow_cell()
Attribute yellow_cell.VB_ProcData.VB_Invoke_Func = "y\n14"
'
' yellow_cell Macro
' Macro recorded 1/29/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+y
'
    With Selection.Interior
        .ColorIndex = 36
        .Pattern = xlSolid
    End With
End Sub
Sub no_color()
Attribute no_color.VB_ProcData.VB_Invoke_Func = "n\n14"
'
' no_color Macro
' Macro recorded 1/29/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+n
'
    Selection.Interior.ColorIndex = xlNone
End Sub
Sub green_cell()
Attribute green_cell.VB_ProcData.VB_Invoke_Func = "g\n14"
'
' green_cell Macro
' Macro recorded 1/29/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+g
'
    Selection.Interior.ColorIndex = 34
End Sub

Sub checkId()
'
' test Macro
' Macro recorded 11/26/2008 by cbhuds
'
    For ctr = 1 To 200
        Set leftCell = ActiveSheet.Cells(ctr, 1)
        Set rightCell = ActiveSheet.Cells(ctr, 4)
        If rightCell.Value <> "" And leftCell.Value <> "" Then
            If (leftCell.Value = rightCell.Value) Then
                ActiveSheet.Cells(ctr, 6).Select
                With Selection.Interior
                    .ColorIndex = 6
                    .Pattern = xlSolid
                End With
            Else
                ActiveSheet.Cells(ctr, 6).Select
                With Selection.Interior
                    .ColorIndex = 7
                    .Pattern = xlSolid
                End With
            End If
        End If
    Next ctr
End Sub

Sub compare()
    Call checkId
    Call checkText
End Sub
Sub checkText()
'
' test Macro
' Macro recorded 11/26/2008 by cbhuds
'
    For ctr = 1 To 200
        Set leftCell = ActiveSheet.Cells(ctr, 2)
        Set rightCell = ActiveSheet.Cells(ctr, 5)
        If rightCell.Value <> "" And leftCell.Value <> "" Then
            If (leftCell.Value = rightCell.Value) Then
                ActiveSheet.Cells(ctr, 7).Select
                With Selection.Interior
                    .ColorIndex = 6
                    .Pattern = xlSolid
                End With
            Else
                ActiveSheet.Cells(ctr, 7).Select
                With Selection.Interior
                    .ColorIndex = 7
                    .Pattern = xlSolid
                End With
            End If
        End If
    Next ctr
End Sub

Sub extractClsName()

    Dim tmp As String
    Dim last_col As Long
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    
    For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
        If InStr(1, c.Value, "::", vbTextCompare) Then
            colon = InStrRev(c.Value, "::", , vbTextCompare)
            diff = (Len(c.Value) - 1) - colon
            Debug.Print Right(c.Value, diff)
        End If
    Next c

End Sub

Sub color_red_text()
'
' color_red_text Macro
' Macro recorded 3/27/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+r
'
    Selection.Font.ColorIndex = 3
    Selection.Font.Bold = True
End Sub

Sub color_purple_text()
Attribute color_purple_text.VB_ProcData.VB_Invoke_Func = "k\n14"
'
' color_purple_text Macro
' Macro recorded 3/27/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+p
'
    Selection.Font.ColorIndex = 54
    Selection.Font.Bold = True
End Sub


Sub color_black_text()
'
' color_black_text Macro
' Macro recorded 3/27/2009 by cbhuds
'
' Keyboard Shortcut: Ctrl+o
'
    Selection.Font.ColorIndex = 1
    Selection.Font.Bold = False
End Sub

Public Sub process_search_request()

' Searches for strings, colors their cell background,
' and displays those not found.

    Dim input_str As String
    Dim cleanList() As String
    Dim idx As Integer
    idx = 0
    Dim commaList As String
    
    ' set commaList
    commaList = searchStr
    
    ' set cell color
    col = cellColor
    
    ' set case sensitive
    caseSens = caseSensitive
    
    ' set look at whole cell
    If wholeCell Then
        entireCell = xlWhole
    Else
        entireCell = xlPart
    End If
    
    ' set flag to copy to another sheet
    Dim cpy As String
    cpy = "" & copyToSheet
    
    ' create list based on comma delimiter
    mylist = Split(commaList, ",", , vbTextCompare)
    
    ' resize based on actual number of items in input
    ReDim Preserve cleanList(UBound(mylist))
    
    ' reassign elements to cleanList
    For Each i In mylist
        If Len(i) > 0 Then
            cleanList(idx) = Trim(i)
            idx = idx + 1
        End If
    Next i
    
    Debug.Print Join(cleanList, ",")
    
    Dim tmp As String
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    Dim c As Range
    Dim notFound() As String
    Dim nfIdx As Integer
    ReDim notFound(1)
    Dim rangeOfCells As Range
    
    ' assign cell background color
    Application.ReplaceFormat.Interior.ColorIndex = col
    
    ' loop thru all items in cleanList
    For Each v In cleanList
        
        found = False
        
        ' loop thru all cells
        For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
    
            ' if v is found in cell
            If InStr(1, c.Value, v, vbTextCompare) Then
            
                ' set background to yellow
                c.Replace What:=v, Replacement:="", LookAt:=entireCell, _
                SearchOrder:=xlByRows, MatchCase:=caseSens, SearchFormat:=False, _
                ReplaceFormat:=True
                found = True
                Debug.Print "cValue=", c.Value, " v=", v
                
                ' copy to other sheet
                If cpy <> "None" Then
                    'Gather the range of cells in the row with matching data.
                    Set rangeOfCells = Worksheets(spreadsheetWithData).Range(Cells(c.row, 1), Cells(c.row, last_col))
'                    For Each d In rangeOfCells
'                        Debug.Print d.row, ", ", d.Column
'                    Next d
                    
                    Call find_and_copy(cpy, rangeOfCells)
                    
                End If
                
            End If
        Next c
        
        ' else not found, add to notFound list
        If Not found Then
        
            notFound(nfIdx) = v
            
            ' grow list by 1
            ReDim Preserve notFound(nfIdx + 1)
            nfIdx = nfIdx + 1
        End If
    Next v
    
    msg = Join(notFound, ", ")
    UserForm2.TextBox1.Value = msg
    UserForm2.Show
    Exit Sub
    
End Sub
Sub appendSpecialChar()
' Just appends the pound char to the end of each cell in the
' active area of the worksheet.  Good for parsing.

    Dim specChar As String
    specChar = "#"

    Dim last_col As Long
    Dim last_row As Long
    last_col = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).Column)
    last_row = Val(ActiveSheet.Cells.SpecialCells(xlLastCell).row)
    
    For Each c In Range(Cells(1, 1), Cells(last_row, last_col))
        oldStr = c.Value
        If oldStr = "" Then
            c.Value = specChar
        End If
        newStr = "'" & oldStr & specChar
        tmp = Replace(c.Value, oldStr, newStr, , , vbTextCompare)
        c.Value = tmp
    Next c

End Sub


Sub find_text()

    UserForm1.Show
    
End Sub
