<jsp:include page="header.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/enduser-account-creation">Create User</a></li>
            <li><a href="/dashboard/enduser-lists">EndUsers List</a></li>
            <li><a href="/dashboard/status" >Status</a></li>
            <li><a href="/dashboard/blood-report">Blood Report</a></li>
            <li><a class="active" href="/dashboard/coins-value">Coins Value</a></li>
            <li><a href="/dashboard/coins-report" >Coins Report</a></li>
        
        </ul>
    </aside>
    <div style="margin:20px auto">
        <main class="content">
            
            <div style="display: inline-block; margin-right: 20px;">
                <table style="border-collapse: collapse; width: 300px;">
                    <thead>
                        <tr>
                            <th colspan="3" style="background-color: #acbca5; text-align: center; color: black;"><strong>Donor</strong></th>
                        </tr>
                        <tr>
                            <th>Blood Group</th>
                            <th>Unit</th>
                            <th>Coins</th>
                        </tr>
                    </thead>
                    <tbody>
                            <tr>
                                <td>A+</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("A+")}</td>
                            </tr>
                            <tr>
                                <td>A-</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("A-")}</td>
                            </tr>
                            <tr>
                                <td>B+</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("B+")}</td>
                            </tr>
                            <tr>
                                <td>B-</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("B-")}</td>
                            </tr>
                            <tr>
                                <td>AB+</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("AB+")}</td>
                            </tr>
                            <tr>
                                <td>AB-</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("AB-")}</td>
                            </tr>
                            <tr>
                                <td>O+</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("O+")}</td>
                            </tr>
                            <tr>
                                <td>O-</td>
                                <td>1</td>
                                <td>${coinPrice.donationPrice("O-")}</td>
                            </tr>
                            
                    </tbody>
                </table>
            </div>
            
            <div style="display: inline-block;">
                <table style="border-collapse: collapse; width: 300px;">
                    <thead>
                        <tr>
                            <th colspan="3" style="background-color: #acbca5; text-align: center; color: black;"><strong>Receiver</strong></th>
                        </tr>
                        <tr>
                            <th>Blood Group</th>
                            <th>Unit</th>
                            <th>Coins</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>A+</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("A+")}</td>
                        </tr>
                        <tr>
                            <td>A-</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("A-")}</td>
                        </tr>
                        <tr>
                            <td>B+</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("B+")}</td>
                        </tr>
                        <tr>
                            <td>B-</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("B-")}</td>
                        </tr>
                        <tr>
                            <td>AB+</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("AB+")}</td>
                        </tr>
                        <tr>
                            <td>AB-</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("AB-")}</td>
                        </tr>
                        <tr>
                            <td>O+</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("O+")}</td>
                        </tr>
                        <tr>
                            <td>O-</td>
                            <td>1</td>
                            <td>${coinPrice.receivePrice("O-")}</td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </main>
    </div>

</div>
<jsp:include page="footer.jsp"/>
