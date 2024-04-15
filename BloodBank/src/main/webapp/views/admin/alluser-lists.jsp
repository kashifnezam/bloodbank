<jsp:include page="header.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/agent-account-creation">Register Agent</a></li>
            <li><a href="/dashboard/alluser-lists" class="active">User Lists</a></li>
            <li><a href="/dashboard/blood-request">Blood Requests</a></li>
            <li><a href="/dashboard/coin-request-list">Coin Requests</a></li>
            <li><a  href="/dashboard/blood-report">Blood Report</a></li>
            <li><a  href="/dashboard/available-stock" >Available Stock</a></li>
            <li><a href="/dashboard/coins-report" >Coins Report</a></li>
        
        </ul>
    </aside>
    
    <main class="content">
        <h1>Blood Bank Dashboard </h1>
        <h3 style="color:green; text-align:center"> ${operation} </h3>
        <div class="action-buttons">
            <!-- Sorting options -->
            <form action="/dashboard/alluser-lists" method="post">
                <select name="sortOption">
                    <option value="select">Select Options</option>
                    <option value="name">Sort by Name</option>
                    <option value="createdBy">Sort CreatedBy</option>
                    <option value="bloodGroup">Sort by Blood Group</option>
                </select>
                <button type="submit">Sort</button>
                
                
                <!-- Filtering options -->
                <select id="filterOption" name="filterOption">
                    <option value="select">Select Options</option>
                    <option value="activeUsers">Active Users</option>
                    <option value="notLoggedInUser">Not LoggedIn Users</option>
                    <option value="byAgent">By Agent</option>
                    <option value="byUsername">By Username</option>
                    <option value="createdBetween">Created Between</option>
                </select>
                <input type="text" id="usernameInput" name="username" placeholder="Enter Username"
                       style="display: none;">
                <input type="text" id="agentInput" name="agent" placeholder="Enter Agent" style="display: none;">
                <input type="date" id="startDate" name="startDate" placeholder="Start Date" style="display: none;">
                <input type="date" id="endDate" name="endDate" placeholder="End Date" style="display: none;">
                <button type="submit">Filter</button>
            </form>
        
        </div>
        <div class="enduser-list">
            <table>
                <thead>
                    <tr>
                        <th>Serial No.</th>
                        <th>Name</th>
                        <th>Username</th>
                        <th>Creation Time</th>
                        <th>CreatedBy</th>
                        <th>DOB</th>
                        <th>Blood Group</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${allUsers}" varStatus="loop">
                        <tr>
                            <td>${loop.index+1}</td>
                            <td>${user.name}</td>
                            <td>${user.username}</td>
                            <td>${user.creationTime}</td>
                            <td>${user.createdBy}</td>
                            <td>${user.dob}</td>
                            <td>${user.bloodGroup}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty allUsers}">
            <p>No users found</p>
        </c:if>
    </main>
</div>
<jsp:include page="footer.jsp"/>
