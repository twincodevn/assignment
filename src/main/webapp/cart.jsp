<%@page import="java.util.Map"%>
<%@page import="model.Product"%>
<%@page import="model.CartItem"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>

<div class="container-fluid page-header py-5">
    <h1 class="text-center text-white display-6">Cart</h1>
    <!-- Breadcrumb omitted for brevity -->
</div>

<c:choose>
    <c:when test="${empty sessionScope.cart.items}">
        <div class="text-center py-5">
            There are no products in your cart. <a href="shop">Start shopping now!</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Product Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total</th>
                                <th scope="col">Remove</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${sessionScope.cart.items}" var="item">
                                <tr>
                                    <td>
                                        <img src="${item.product.image}" class="img-fluid rounded-circle" style="width: 80px; height: 80px;" alt="${item.product.name}">
                                    </td>
                                    <td><p class="mb-0 mt-4">${item.product.name}</p></td>
                                    <td><p class="mb-0 mt-4">${item.product.price}</p></td>
                                    <td>
                                        
                                        <p class="mb-0 mt-4">${item.quantity}</p>
                                    </td>
                                    <td><p class="mb-0 mt-4">${item.totalPrice}</p></td>
                                    <td>
                                        <!-- Remove item form or button -->
                                        <form action="remove-from-cart" method="post">
                                            <input type="hidden" name="productId" value="${item.product.id}" />
                                            <button type="submit" class="btn btn-danger btn-sm mt-3">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <form action="process-checkout" method="get">
                    <div class="row g-4 text-center align-items-center justify-content-center pt-4">
                        <button  type="submit" class="btn border-secondary py-3 px-4 text-uppercase w-100 text-primary" >Checkout</input>
                </div>
                </form>
                
            </div>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp"></jsp:include>
