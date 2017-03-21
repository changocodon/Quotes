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
    var author = document.getElementById("name" + i);
    var quote = document.getElementById("quote" + i);
    alert(author.textContent + ': ' + quote.textContent);
}

function updateSQuote() {
    var author = document.getElementById("author").value;
    var quoteOfAuthor = document.getElementById("quoteOfAuthor").value;
    var quoteId = document.getElementById("quoteId").innerHTML;
     fetch('/up', {
        method: 'put',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            'name':  author,
            'quote': quoteOfAuthor,
            '_id': quoteId
        })
    })
}