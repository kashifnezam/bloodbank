<jsp:include page="header.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/agent-account-creation">Register Agent</a></li>
            <li><a href="/dashboard/alluser-lists">User Lists</a></li>
            <li><a href="/dashboard/blood-request">Blood Requests</a></li>
            <li><a class="active"  href="/dashboard/coin-request-list">Coin Requests</a></li>
            <li><a href="/dashboard/blood-report">Blood Report</a></li>
            <li><a href="/dashboard/available-stock">Available Stock</a></li>
            <li><a href="/dashboard/coins-report">Coins Report</a></li>
        </ul>
    </aside>
    <!-- Overlay to blur the background -->
    <div class="overlay" id="overlay"></div>
    <main class="content">
        <div class="main-content">
            <h2 style="text-align:center">Coins Requested List</h2>
            <h3 style="text-align:center; color:red">${errorMsg}</h3>
            <h3 style="color:green; text-align:center"> ${success} </h3>
            <br>
            <table >
                <thead>
                    <tr>
                        <th>Serial No.</th>
                        <th>Username</th>
                        <th>Blood Group</th>
                        <th>Request Amount</th>
                        <th>Requested At</th>
                        <th>DOB</th>
                        <th>Request ID</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${coinRequests}" var="coinReq" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${coinReq.user.username}</td>
                            <td>${coinReq.user.bloodGroup}</td>
                            <td>${coinReq.coins}</td>
                            <td>${coinReq.creationTime}</td>
                            <td>${coinReq.user.dob}</td>
                            
                            <td>${coinReq.id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${coinReq.status eq 'pending'}">
                                        <div class="button-group">
                                            <form action="#" method="POST">
                                                <input name="coinStatus" type="hidden" value="accepted">
                                                <input name="id" type="hidden" value="${coinReq.id}">
                                                <input name="coins" type="hidden" value="${coinReq.coins}">
                                                <button class="btn accept-btn" type="submit">Accept</button>
                                            </form>
                                            <form action="#" method="POST">
                                                <input type="hidden" name="coinStatus" value="rejected">
                                                <input type="hidden" name="id" value="${coinReq.id}">
                                                <button type="submit" class="btn reject-btn">Reject</button>
                                            </form>
                                        </div>
                                       
                                    </c:when>
                                    <c:when test="${coin.status eq 'rejected'}">
                                        <span class="btn rejected-btn">Rejected</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="btn accepted-btn">Accepted</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
    </main>
</div>

<jsp:include page="footer.jsp"/>
