<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>
    <div style="margin: 200px 0 0 100px;">
        <h2 class="text-center">Fruit list</h3>
            <a href="add-product" class="mt-3" ><button class="btn border-secondary rounded-pill px-4 py-3 text-primary" type="button">Add fruit</button</a>
    </div>
    <div class="container mt-3">
        <div class="container ">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Image</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th>Weight</th>
                            <th>Country</th>
                            <th>Quality</th>
                            <th>Test</th>
                            <th>Action</th>

                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${fruits}" var="fruit">
                        <tr>
                            <td>
                                <p class="mb-0 mt-4">${fruit.id}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.name}</p>
                            </td>

                            <th scope="row">
                                <div class="d-flex align-items-center">
                                    <img src="../${fruit.image}" class="img-fluid me-5 rounded-circle" style="width: 80px; height: 80px;" alt="">
                                </div>
                            </th>
                            <td>
                                <p class="mb-0 mt-4">${fruit.getCategory().name}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.price} $</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.description}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.weight}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.origin}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.quality}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">${fruit.passed == '1' ? 'Passed' : 'Not pass'}</p>
                            </td>


                            <td>
                                <div class="input-group quantity mt-4" style="width: 100px;">
                                    <div class="input-group-btn">
                                        <a href="update-product?id=${fruit.id}">
                                            <button  class="btn btn-sm btn-minus rounded-circle bg-light border" >
                                                <i class="bi bi-pen">Update</i>
                                            </button>
                                        </a>
                                    </div>
                                    <a href="#" onclick="confirmDelete('${fruit.id}');">
                                        <button class="btn btn-sm btn-minus rounded-circle bg-light border">
                                            <i class="bi bi-pen">Delete</i>
                                        </button>
                                    </a>


                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>



<script>
    function confirmDelete(id) {
        var confirmAction = confirm("Are you sure you want to delete this product?");
        if (confirmAction) {
            // User pressed OK, proceed with deletion
            window.location.href = "delete-product?id=" + id;
        } else {
            // User pressed Cancel, do nothing
            console.log("Deletion cancelled");
        }
    }
</script>