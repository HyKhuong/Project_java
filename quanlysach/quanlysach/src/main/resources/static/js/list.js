$(document).ready(function () {
    // Fetch books from API
    $.ajax({
        url: 'http://localhost:8080/api/v1/books',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let trHTML = '';
            $.each(data, function (i, item) {
                trHTML += '<tr id="book-' + item.id + '">' +
                          '<td>' + item.id + '</td>' +
                          '<td>' + item.title + '</td>' +
                          '<td>' + item.author + '</td>' +
                          '<td>' + item.price + '</td>' +
                          '<td>' + item.categoryName + '</td>' +
                          '<td sec:authorize="hasAnyAuthority(\'ADMIN\')">'+
                          '<a href="#" class="text-primary" onclick="editBook(' + item.id + ')">Edit</a>' +
                          '<a href="#" class="text-danger" onclick="apiDeleteBook(' + item.id + ')">Delete</a>' +
                          '</td>'+
                          '</tr>';
            });
            $('#book-table-body').append(trHTML);
        }
    });
});

function apiAddBook() {
    const book = {
        title: $('#book-title').val(),
        author: $('#book-author').val(),
        price: $('#book-price').val(),
        categoryName: $('#book-category').val()
    };

    $.ajax({
        url: 'http://localhost:8080/api/v1/books/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(book),
        success: function (data) {
            alert('Book added successfully!');
            location.reload(); // Reload the page to see the new book
        }
    });
}

function apiUpdateBook() {
    const bookId = $('#book-id').val();
    const book = {
        title: $('#book-title').val(),
        author: $('#book-author').val(),
        price: $('#book-price').val(),
        categoryName: $('#book-category').val()
    };

    $.ajax({
        url: 'http://localhost:8080/api/v1/books/update/' + bookId,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(book),
        success: function (data) {
            alert('Book updated successfully!');
            location.reload(); // Reload the page to see the updated book
        }
    });
}

function apiDeleteBook(id) {
    if (confirm('Are you sure you want to delete this book?')) {
        $.ajax({
            url: 'http://localhost:8080/api/v1/books/delete/' + id,
            type: 'DELETE',
            success: function () {
                alert('Book deleted successfully!');
                $('#book-' + id).remove();
            }
        });
    }
}

function editBook(id) {
    // Fetch the book details and populate the form
    $.ajax({
        url: 'http://localhost:8080/api/v1/books/' + id,
        type: 'GET',
        success: function (data) {
            $('#book-id').val(data.id);
            $('#book-title').val(data.title);
            $('#book-author').val(data.author);
            $('#book-price').val(data.price);
            $('#book-category').val(data.categoryName);
        }
    });
}