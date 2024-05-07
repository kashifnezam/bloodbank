<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <style>
            /* Reset styles */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
    
            }
    
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
    
            }
    
            .container {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
    
            }
    
            /* Header styles */
            header {
                background-color: #333;
                color: #fff;
                padding: 10px;
            }
    
            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
    
            .logo a {
                color: #fff;
                text-decoration: none;
                font-size: 24px;
                font-weight: bold;
            }
    
            .user-info {
                display: flex;
                align-items: center;
            }
    
            .user-info span {
                margin-right: 10px;
            }
    
            .logout {
                color: #fff;
                text-decoration: none;
                padding: 5px 10px;
                background-color: #ff6347;
                border-radius: 4px;
            }
    
            /* Main content styles */
            .main {
                display: flex;
                flex: 1;
            }
    
            .sidebar {
                background-color: #939b98;
                padding: 20px;
                min-width: 190px;
            }
    
            .sidebar ul {
                list-style-type: none;
            }
    
            .sidebar li {
                margin-bottom: 10px;
            }
    
            .sidebar a {
                color: #333;
                text-decoration: none;
                padding: 10px;
                display: block;
            }
    
            .sidebar a:hover{
                background-color: #f8fffd;
                border-radius: 10px;
    
    
            }
            .sidebar a.active {
                color: white;
                border-radius: 10px;
                background-color: #282d2b;
            }
    
            .content {
                padding: 20px;
                flex: 1;
            }
    
            /* My Own CSS */
    
                 h1 {
                      text-align: center;
                      margin-bottom: 5px;
                      color: #2c3e50;
                  }
                  .admin-profile {
                      margin: auto;
                      height: 80%;
                      width: 60%;
                      margin-bottom: 30px;
                      padding: 20px;
                      background-color: #34495e;
                      color: #ecf0f1;
                      border-radius: 10px;
                  }
    
                  .admin-profile h2 {
                      margin-bottom: 15px;
                  }
                  
                  table {
                      width: 100%;
                      border-collapse: collapse;
                  }
                  th, td {
                      padding: 10px;
                      text-align: left;
                      border-bottom: 2px solid #bdc3c7;
                      border-right: 0.1px solid #bdc3c7;
                  }
                  th {
                      background-color: #2c3e50;
                      color: #ecf0f1;
                      font-weight: bold;
                      text-transform: uppercase;
                  }
                  td {
                      background-color: #ecf0f1;
                  }
    
                  .agent-form{
                    width:50%;
                    margin: auto;
                    height: 70%
                  }
                  .enduser-list{
                    width:90%;
                    margin:auto;
                  }
                  form {
                          display: flex;
                          flex-direction: column;
                      }
                      .form-row {
                          display: flex;
                          justify-content: space-between;
                          margin-bottom: 15px;
                      }
                      .form-row label,
                      .form-row input,
                      .form-row textarea {
                          flex: 1;
                      }
                      label {
                          margin-bottom: 8px;
                          font-weight: bold;
                      }
                      input[type="text"],
                      input[type="email"],
                      input[type="date"],
                      input[type="number"],
                      textarea {
                          padding: 10px;
                          border: 1px solid #ccc;
                          border-radius: 5px;
                          font-size: 16px;
                      }
                      input[type="submit"] {
                          background-color: #007bff;
                          color: #fff;
                          border: none;
                          padding: 12px 20px;
                          border-radius: 5px;
                          font-size: 16px;
                          cursor: pointer;
                      }
                      input[type="submit"]:hover {
                          background-color: #0056b3;
                      }
    
                /* Request List Css */
               /* Button group container */
               .button-group {
                   display: flex;
                   gap: 1px; /* Add gap between buttons */
               }
    
               /* Button styling */
               .btn {
                   padding: 10px 10px;
                   font-size: 14px;
                   font-weight: bold;
                   text-decoration: none;
                   text-align: center;
                   border-radius: 4px;
                   transition: background-color 0.3s ease;
                   border: 1px solid transparent; /* Add border */
               }
    
               .accept-btn {
                   background-color: #28a745; /* Green color for accept */
                   color: #fff;
               }
    
               .reject-btn {
                   background-color: #dc3545; /* Red color for reject */
                   color: #fff;
               }
    
               .rejected-btn {
                   background-color: #6c757d; /* Gray color for rejected */
                   color: #fff;
               }
    
               .accepted-btn {
                   background-color: #007bff; /* Blue color for accepted */
                   color: #fff;
               }
    
               /* Hover effect */
               .btn:hover {
                   opacity: 0.8;
               }
    
               /* Active effect */
               .btn:active {
                   transform: translateY(1px);
               }
    
               /* Add border color on hover and focus */
               .btn:hover,
               .btn:focus {
                   border-color: #adb5bd; /* Light gray */
               }
    
                /* CSS for filter & sorting */
                .action-buttons {
                    margin-bottom: 20px;
                    margin-left: 5px;
                }
    
                .action-buttons form {
                    display: inline-block;
                    margin-right: 20px;
                }
    
                .action-buttons select {
                    padding: 8px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                }
    
                .action-buttons input[type="text"] {
                    padding: 8px;
                    width: 150px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    margin-right: 5px;
                }
    
                .action-buttons button {
                    padding: 8px 15px;
                    background-color: #4CAF50;
                    color: white;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                }
    
                .action-buttons button:hover {
                    background-color: #45a049;
                }
    
            /* Styles for the Status Rejection Remark */
                .overlay {
                  display: none;
                  position: fixed;
                  top: 0;
                  left: 0;
                  width: 100%;
                  height: 100%;
                  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black */
                  z-index: 9998; /* Ensure the overlay is behind the popup */
                }
              
                /* Styles for the popup */
                .popup {
                  display: none;
                  position: fixed;
                  top: 50%;
                  left: 50%;
                  transform: translate(-50%, -50%);
                  background-color: #ffffff;
                  padding: 30px;
                  border: 2px solid #ccc;
                  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
                  z-index: 9999;
                  max-width: 400px;
                  width: 90%;
                  border-radius: 8px;
                  font-family: Arial, sans-serif;
                }
              
                /* Styles for form elements */
                .popup form {
                  text-align: left;
                }
              
                .popup h2 {
                  margin-top: 0;
                  margin-bottom: 20px;
                  font-size: 24px;
                  color: #333;
                }
              
                .popup label {
                  display: block;
                  margin-bottom: 10px;
                  font-size: 16px;
                  color: #555;
                }
              
                .popup textarea {
                  width: 120%;
                  height: 100px;
                  margin-bottom: 20px;
                  padding: 10px;
                  border: 1px solid #ccc;
                  border-radius: 4px;
                  font-size: 14px;
                  resize: none;
                }
              
                .popup input[type="submit"] {
                  padding: 10px 20px;
                  background-color: #4CAF50;
                  color: white;
                  border: none;
                  border-radius: 4px;
                  cursor: pointer;
                  font-size: 16px;
                }
              
                .popup input[type="submit"]:hover {
                  background-color: #45a049;
                }
                                /* Styling for the popup */
                .enduser-status-popup {
                  display: none;
                  position: fixed;
                  top: 50%;
                  left: 50%;
                  transform: translate(-50%, -50%);
                  background-color: #ffffff;
                  padding: 20px;
                  border: 2px solid #3498db;
                  border-radius: 10px;
                  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.75);
                  z-index: 9999;
                  animation: fadeIn 0.5s ease-in-out;
                  max-width: 400px;
                  width: 80%;
                  text-align: center;
                }
                                 /* Styling for the info icon */
                .info-icon.enduser-status {
                    padding-bottom: 12px;
                    cursor: pointer;
                    color: black;
                    font-size: 12px; /* Adjust the size of the icon */
                    display: inline-block;
                    width: 20px; /* Set the width and height of the circular area */
                    height: 20px;
                    line-height: 18px;
                    text-align: center;
                    border-radius: 50%; /* Create a circular shape */
                    border: 2px solid black; /* Border color */
                  }
              
                /* Styling for the heading */
                .popup-heading {
                  font-size: 20px;
                  color: #333333;
                  margin-bottom: 15px;
                }
              
                /* Styling for the reason text */
                .popup-text {
                  font-size: 16px;
                  color: #666666;
                  margin-bottom: 20px;
                }
              
                /* Styling for the close button */
                .popup-close-button {
                  background-color: #3498db;
                  color: #ffffff;
                  border: none;
                  padding: 10px 20px;
                  border-radius: 5px;
                  cursor: pointer;
                  font-size: 16px;
                  transition: background-color 0.3s ease;
                }
              
                .popup-close-button:hover {
                  background-color: #2980b9;
                }
              
                /* Keyframes for fadeIn animation */
                @keyframes fadeIn {
                  from { opacity: 0; }
                  to { opacity: 1; }
                }
                
            /* Footer styles */
            footer {
                background-color: #333;
                color: #fff;
                padding: 10px;
                text-align: center;
            }
        
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!-- Include DataTables CSS -->
        <script src="https://cdn.datatables.net/2.0.4/js/dataTables.js"></script>
        <link href="https://cdn.datatables.net/2.0.4/css/dataTables.dataTables.css" rel="stylesheet"/>
        
        <!--  Sweet Alert  -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!--  Date Picker     -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.10/datepicker.min.js" integrity="sha512-RCgrAvvoLpP7KVgTkTctrUdv7C6t7Un3p1iaoPr1++3pybCyCsCZZN7QEHMZTcJTmcJ7jzexTO+eFpHk4OCFAg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.10/datepicker.min.css" integrity="sha512-YdYyWQf8AS4WSB0WWdc3FbQ3Ypdm0QCWD2k4hgfqbQbRCJBEgX0iAegkl2S1Evma5ImaVXLBeUkIlP6hQ1eYKQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!---- Moment js -->
       
    </head>
    <body>
        <div class="container">
            <header>
                <nav class="navbar">
                    <div class="logo">
                        <a href="#">Blood Bank</a>
                    </div>
                    <div class="user-info">
                        <span>Welcome, ${data   .name} </span>
                        <a href="/logout" class="logout">Logout</a>
                    </div>
                </nav>
            </header>
