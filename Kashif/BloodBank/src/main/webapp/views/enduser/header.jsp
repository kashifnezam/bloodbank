<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <title>Customer Dashboard</title>
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
                background-color: #f7f7f7;
                color: #333;
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
                background-color: #333;
                color: #fff;
                padding: 20px;
                width: 200px;
            }
    
            .sidebar ul {
                list-style-type: none;
                padding: 0;
            }
    
            .sidebar li {
                margin-bottom: 10px;
            }
    
            .sidebar a {
                color: #fff;
                text-decoration: none;
                padding: 10px;
                display: block;
            }
    
            .sidebar a:hover,
            .sidebar a.active {
                border-radius: 10px;
                background-color: #555;
            }
    
            .sidebar a.active {
                background-color: #d32f2f;
            }
    
            .main-content {
                flex: 1;
                padding: 65px;
            }
    
            .dashboard-container, {
                max-width: 800px;
                margin: 0 auto;
                background-color: #fff;
                padding: 30px;
                border-radius: 5px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
    
            h2 {
                text-align: center;
                color: #d32f2f;
                margin-bottom: 30px;
            }
    
            .info-section {
                margin-bottom: 30px;
            }
    
            .info-section h3 {
                margin-bottom: 10px;
            }
    
            .info-section p {
                margin-bottom: 5px;
            }
    
            /* --------------  My CSS ---------------*/
    
            /* Donate/Receive Blood */
            .donation-form {
                text-align: end;
                max-width: 400px;
                margin: 0 auto;
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
    
            .donation-form h2 {
                text-align: center;
                margin-bottom: 20px;
            }
    
            form {
                display: flex;
                flex-direction: column;
            }
    
            .form-group {
                margin-bottom: 15px;
            }
    
            label {
                font-weight: bold;
                margin-bottom: 5px;
            }
    
    
            input[type="text"],
            input[type="number"] {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
    
            button[type="submit"] {
                padding: 10px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
    
            button[type="submit"]:hover {
                background-color: #0056b3;
            }
    
            /* CSS for Table Status */
                table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    th, td {
                        padding: 8px;
                        text-align: left;
                        border-bottom: 1px solid #ddd;
                    }
                    tr:nth-child(even) {
                        background-color: #f2f2f2;
                    }
                    .accepted {
                        background-color: #c9f9c9;
                    }
                    .rejected {
                        background-color: #ffb3b3;
                    }
                    .pending {
                        background-color: #ffff99;
                    }
                    .blood-group-info {
                        display: inline-block;
                        margin-bottom: 15px;
                        padding: 10px;
                        text-align: center;
                        background-color: #f5f5f5;
                        color: #333;
                        border: 1px solid #ddd;
                        border-radius: 5px;
                    }
                    .accepted-info {
                        background-color: #c9f9c9;
                        color: #228B22;
                    }
                    /* Styling thead */
                    .table-header {
                        background-color: #007bff;
                        color: white;
                    }
                    .table-header th {
                        padding: 10px;
                        font-weight: bold;
                        text-transform: uppercase;
                        border-bottom: 2px solid #ddd; /* increase border size */
                    }
    
                /* endUser-status design popup */
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
                  margin: 5px;
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
                .custom-btn {
                  background-color: #007bff; /* Change background color */
                  color: #fff; /* Change text color */
                  border: none; /* Remove border */
                  padding: 0.375rem 0.75rem; /* Adjust padding */
                  border-radius: 0.25rem; /* Adjust border radius */
                }

                .custom-badge {
                  background-color: #6c757d; /* Change badge background color */
                  color: #fff; /* Change badge text color */
                  font-size: 0.8rem; /* Adjust badge font size */
                  margin-left: 5px; /* Adjust badge position */
                }
                .logout-container {
                    width:150px;
                    position: absolute;
                    bottom: 50px; /* Adjust as needed */
                    left: 20px; /* Adjust as needed */
                }
                
                .logout {
                    color: #fff;
                    text-decoration: none;
                    padding: 10px;
                    background-color: grey;
                    border-radius: 8px;
                    display: block;
                }
                
                /* Styling for logout button on hover */
                .logout:hover {
                    background-color: #d9534f;
                }
               
             .notification-panel {
         
              position: fixed;
              top: 60px;
              right: 20px;
              max-width: 300px;
              background-color: #ffffff;
              border: 1px solid #e0e0e0;
              border-radius: 8px;
              box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
              max-width: 320px;
              max-height: 450px; /* Set a fixed height for the panel */
              overflow-y: auto; /* Enable vertical scrolling */  display:none;
              pointer-events: none;
              transition: opacity 0.3s ease;
              animation: fadeIn 0.3s ease;
            }

                .notification-panel.show {
                  display:block;
                  pointer-events: auto;
                }
                
                .notification {
                  border-bottom: 1px solid #e0e0e0;
                }
                
                .notification:last-child {
                  border-bottom: none;
                }
                
                .notification-body {
                  padding: 15px;
                }
                
                .notification-order-id {
                  font-size: 14px;
                  color: #757575;
                  margin-bottom: 5px;
                }
                
                .notification-title {
                  margin: 0;
                  font-size: 18px;
                  color: #333333;
                }
                
                .notification-footer {
                  padding: 0 15px 15px;
                }
                
                .notification-date {
                  margin: 0;
                  font-size: 14px;
                  color: #757575;
                }
                
                .notification-title-bar {
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  padding: 15px;
                  background-color: #d4edda;
                  border-bottom: 1px solid #c3e6cb;
                  border-radius: 8px 8px 0 0;
                }
                
                .close-btn {
                  background: none;
                  border: none;
                  cursor: pointer;
                  color: #007bff;
                  font-size: 20px;
                }
                
                .close-btn:hover {
                  color: #0056b3;
                }
                .notification-count {
                  background-color: #007bff;
                  color: #ffffff;
                  font-size: 12px; /* Adjust font size as needed */
                  padding: 2px 6px; /* Adjust padding as needed */
                  border-radius: 50%;
                  margin-left: 5px;
                }
                
                
              /* Styling for Coin Request  */
              
                 .coin-request-container {
                 width: 450px;
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .coin-request-form {
                margin-top: 20px;
            }
            .coin-request-form label {
                display: block;
                margin-bottom: 5px;
                color: #333;
            }
            .coin-request-form input[type="text"],
            .coin-request-form textarea {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }
            .coin-request-form input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .coin-request-form input[type="submit"]:hover {
                background-color: #45a049;
            }
             /* Styling for profile picture */
.profile-picture {
    float: left; /* Align to the left */
    width: 50px; /* Adjust the size as needed */
    height: 50px; /* Adjust the size as needed */
    border-radius: 50%;
    overflow: hidden;
    border: 2px solid #fff; /* Add border if needed */
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3); /* Add shadow if needed */
    margin-right: 10px; /* Add some space between the picture and the edge */
}

.profile-picture img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

                
                /* Footer styles */
                footer {
                    background-color: #333;
                    color: #fff;
                    padding: 10px;
                    text-align: center;
                }
        </style>
    </head>
    <body>
        <div class="container">
            <header>
                <!-- Profile picture display (conditionally) -->
                <c:if test="${not empty data.profilePicURL}">
                    <div class="profile-picture">
                            <img src="${data.profilePicURL}" alt="Profile Picture">
                    </div>
                </c:if>
                <nav class="navbar">
                    <div class="logo">
                        <a href="#">${data.name}</a>
                    </div>
                    
                    
                    <div class="user-info">
                        <button id="notificationBtn" type="button">
                            <h3 class="popup-heading">
                                Notifications
                                <span class="notification-count" id="notificationCount">
                                    ${notificationList.size()}
                                </span>
                            </h3>
                        </button>
                       
                    </div>
                    
                    
                    <div class="notification-panel " id="notificationPanel">
                        <c:if test="${notificationList.size()>0}">
                            <div class="notification-title-bar">
                                <h3 class="popup-heading">Notifications</h3>
                                <button class="close-btn" type="button">&times;</button>
                            </div>
                        </c:if>
                        <c:forEach items="${notificationList}" var="notification">
                            <div class="notification">
                                <div class="notification-body">
                                    <p class="notification-order-id">Request ID: ${notification.id}</p>
                                    <div class="notification-header">
                                        <h4 class="notification-title">${notification.notification}</h4>
                                    </div>
                                </div>
                                <div class="notification-footer">
                                    <p class="notification-date">
                                        <fmt:formatDate pattern="MMM dd, yyyy HH:mm a"
                                                        value="${notification.updatedTime}"/>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                
                
                </nav>
            </header>
            <div class="logout-container">
                <a class="logout" href="/logout">Logout</a>
            </div>