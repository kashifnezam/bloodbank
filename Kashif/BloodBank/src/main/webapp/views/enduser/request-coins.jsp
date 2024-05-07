<jsp:include page="header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/donate-blood">Donate Blood</a></li>
            <li><a href="/dashboard/receive-blood">Receive Blood</a></li>
            <li><a href="/dashboard/status">Status</a></li>
            <li><a href="/dashboard/request-coins" class="active">Request Coins</a></li>
            <li><a href="#">Withdraw Coins</a></li>
        </ul>
    
    </aside>
    <div class="main-content">
        <h3 style="color:red; text-align:center"> ${errorMsg} </h3>
        <h3 style="color :green; text-align:center">${success} </h3>
        <div class="coin-request-container">
            <h1>Request Coins</h1>
            <form class="coin-request-form" method="post">
                <label for="amount">Amount of Coins:</label>
                <input type="number" id="amount" name="coins" required>
                <label for="reason">Reason for Request:</label>
                <textarea id="reason" name="reason" rows="4" required></textarea>
                <input type="submit" value="Submit Request">
            </form>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp" />