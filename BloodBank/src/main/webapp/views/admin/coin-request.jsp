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
<!--            <form action="/dashboard/status" method="post" style="display: inline-block; margin-right: 20px;">-->
<!--                -->
<!--                &lt;!&ndash; Filtering options &ndash;&gt;-->
<!--                <select id="filterOption" name="filterOption">-->
<!--                    <option value="select">Select Options</option>-->
<!--                    <option value="createdBetween">Created Between</option>-->
<!--                    <option value="byStatus">By Status</option> &lt;!&ndash; New option for status &ndash;&gt;-->
<!--                </select>-->
<!--                -->
<!--                -->
<!--                -->
<!--                &lt;!&ndash; Start date input &ndash;&gt;-->
<!--                <input id="startDate" name="startDate" placeholder="Start Date" style="display: none;" type="date">-->
<!--                -->
<!--                &lt;!&ndash; End date input &ndash;&gt;-->
<!--                <input id="endDate" name="endDate" placeholder="End Date" style="display: none;" type="date">-->
<!--                -->
<!--                &lt;!&ndash; Status select &ndash;&gt;-->
<!--                <select id="statusSelect" name="status" style="display: none;">-->
<!--                    <option value="select">Select Status</option>-->
<!--                    <option value="accepted">Accepted</option>-->
<!--                    <option value="rejected">Rejected</option>-->
<!--                    <option value="pending">Pending</option>-->
<!--                </select>-->
<!--                -->
<!--                <button type="submit">Filter</button>-->
<!--            </form>-->
<!--            -->
<!--            <br>-->
            <table>
                <thead class="table-header">
                    <tr>
                        <th>Serial No.</th>
                        <th>Username</th>
                        <th>Coin</th>
                        <th>Reason</th>
                        <th>Request Date</th>
                        <th>Request Id</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${coinRequests}" var="request" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${request.user.username}</td>
                            <td>${request.coins}</td>
                            <td>  <span class="info-icon enduser-status" data-remark="${request.reason}">&#8505;</span>
                            </td>
                            <td>${request.creationTime}</td>
                            <td>${request.id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${request.status eq 'pending'}">
                                        <div class="button-group">
                                            <form action="/dashboard/coin-request-list" method="POST">
                                                <input name="coinStatus" type="hidden" value="accepted">
                                                <input name="coins" type="hidden" value="${request.coins}">
                                                <input name="id" type="hidden" value="${request.id}">
                                                <button class="btn accept-btn" type="submit">Accept</button>
                                            </form>
                                            <button class="btn reject-btn" type="button">Reject </button>
                                        </div>
                                        
                                    </c:when>
                                    <c:when test="${request.status eq 'rejected'}">
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
        <!-- Popup -->
        <div class="enduser-status-popup enduser-status" id="enduser-status-popup">
            <div class="popup-heading">Request Reason</div>
            <div class="popup-text" id="popup-reason">
            </div>
            <button class="popup-close-button" onclick="closePopup()">Close</button>
        </div>
    </main>
</div>

<jsp:include page="footer.jsp"/>
