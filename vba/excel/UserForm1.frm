VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} UserForm1 
   Caption         =   "Search Items"
   ClientHeight    =   4995
   ClientLeft      =   45
   ClientTop       =   435
   ClientWidth     =   7680
   OleObjectBlob   =   "UserForm1.frx":0000
   StartUpPosition =   1  'CenterOwner
End
Attribute VB_Name = "UserForm1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Option Explicit

Private Sub btnCancel_Click()
    Unload Me
End Sub

Private Sub btnSearch_Click()
    Module2.spreadsheetWithData = ActiveSheet.Name
    Module2.searchStr = Me.TextBox1.Value
    Module2.cellColor = Me.ComboBox1.Value
    Module2.caseSensitive = Me.CheckBox1.Value
    Module2.wholeCell = Me.CheckBox2.Value
    Module2.copyToSheet = Me.ComboBox2.Value
    Me.Hide
    Call Module2.process_search_request
End Sub

Private Sub CheckBox3_Click()

End Sub

Private Sub UserForm_Initialize()

    Me.TextBox1.Value = ""
    
    ' Initialize color index combobox
    Me.ComboBox1.AddItem "33"
    Me.ComboBox1.AddItem "34"
    Me.ComboBox1.AddItem "35"
    Me.ComboBox1.AddItem "36"
    Me.ComboBox1.AddItem "37"
    Me.ComboBox1.AddItem "38"
    Me.ComboBox1.AddItem "39"
    Me.ComboBox1.AddItem "40"
    Me.ComboBox1.AddItem "41"
    Me.ComboBox1.AddItem "42"
    Me.ComboBox1.AddItem "43"
    Me.ComboBox1.AddItem "44"
    Me.ComboBox1.AddItem "45"
    Me.ComboBox1.AddItem "46"
    Me.ComboBox1.AddItem "47"
    Me.ComboBox1.AddItem "48"
    Me.ComboBox1.Style = fmStyleDropDownList
    Me.ComboBox1.Value = "33"
    
    ' Initialize worksheet name combobox
    Me.ComboBox2.AddItem "None"
    Dim ws As Worksheet
    For Each ws In Worksheets
        Me.ComboBox2.AddItem ws.Name
    Next ws
    Me.ComboBox2.Value = "None"
    
End Sub
