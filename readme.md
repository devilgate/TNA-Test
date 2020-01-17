# Test for The National Archives

This is Martin McCallion's test project for The National Archives. The brief is as follows:

> Given a CSV file (text file that uses a comma to separate values) with the following structure:

	```
	filename, origin, metadata, hash
	file1, London, "a file about London", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
	file2, Surrey, "a file about The National Archives", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564967
	file55, Londom, "London was initially incorrectly spelled as Londom", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
	file4, Penrith, "Lake District National Park info", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564968
	file22, Londom, "National Park Strategy", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564968
	```

> Please write code that allows you to replace all occurrences of a specified value in a specified column (by header) with a new value, and saves a new version of the file.
>
> Given the example above, your function should let you change “Londom” to “London” in the “origin” column.
