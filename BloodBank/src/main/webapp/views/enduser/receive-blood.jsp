<jsp:include page="header.jsp"/>
<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/donate-blood">Donate Blood</a></li>
            <li><a class="active" href="/dashboard/receive-blood">Receive Blood</a></li>
            <li><a href="/dashboard/status">Status</a></li>
            <li><a href="/dashboard/request-coins">Request Coins</a></li>
            <li><a href="#">Withdraw Coins</a></li>
        </ul>
    </aside>
    <div class="main-content">
        <h3 style="color:red; text-align:center"> ${errorMsg} </h3>
        <div class="donation-form" style="max-width: 400px; margin: 0 auto;">
            <h2 style="text-align: center; margin-bottom: 20px;">Receive Blood</h2>
            <form action="/dashboard/receive-blood" method="POST">
                <input id="type" name="type" type="hidden" value="receiver">
                <div class="form-group" style="display: flex; align-items: center; margin-bottom: 10px;">
                    <label for="bloodGroup" style="width: 120px; margin-right: 10px;">Blood Group:</label>
                    <select id="bloodGroup" name="bloodGroup" required style="flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px;">
                        <option value="" disabled selected hidden>Select Blood Group</option>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                    </select>
                </div>
                <div class="form-group" style="display: flex; align-items: center; margin-bottom: 20px;">
                    <label for="quantity" style="width: 120px; margin-right: 10px;">Quantity :</label>
                    <input id="quantity" name="quantity" placeholder="Enter quantity" required type="number" style="flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px;">
                </div>
                <button type="submit" style="background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; width: 100%;">Request for Blood</button>
            </form>
        </div>
        
        <h3 style="color :green; text-align:center">${success} </h3>
    </div>
</div>
<jsp:include page="footer.jsp"/>