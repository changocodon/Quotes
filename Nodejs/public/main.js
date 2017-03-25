function editSQuote(i) {
    var author = document.getElementById("name" + i);
    var quote = document.getElementById("quote" + i);
    var quoteId = document.getElementById("quoteId" + i);

    document.getElementById("author").value = author.textContent;
    document.getElementById("quoteOfAuthor").value = quote.textContent;
    document.getElementById("quoteId").innerHTML = quoteId.textContent;
    alert(author.textContent + ': ' + quote.textContent + quoteId.textContent);

}

function deleteSQuote(i) {
    var quoteId = document.getElementById("quoteId"+ i);

    fetch('/', {
        method: 'delete',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            '_id': quoteId.textContent
        })
    }).then(res => {
        if (res.ok) return res.body

    }).then(data => {
            console.log(data)
            window.location.reload(true)
        })
}

function updateSQuote() {
    var author = document.getElementById("author").value;
    var quoteOfAuthor = document.getElementById("quoteOfAuthor").value;
    var quoteId = document.getElementById("quoteId").innerHTML;
    fetch('/', {
        method: 'put',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            'name': author,
            'quote': quoteOfAuthor,
            '_id': quoteId
        })
    }).then(res => {
        if (res.ok) return res.json()

    }).then(data => {
            console.log(data)
            window.location.reload(true)
        })
}


