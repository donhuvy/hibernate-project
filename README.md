works like this:

Postman: PostRequest: 
http://localhost:8080/products/createProduct
in Body Json:
{
    "productDescription" :
    {
        "descriptionName" : "Colebil",
        "descriptionAbout" : "eheheh"
    },
    "productPrice" : 15.99,
    "productQuantity" : 20
}

Postman RequestPost
http://localhost:8080/categories/create
body Json:
{
    "categoryDescription" :
    {
        "descriptionName" : "Biotics",
        "descriptionAbout" : "kill microbs"
    }
}
then Post:
http://localhost:8080/categories/addProducts/Biotics/Colebil
then Post:
http://localhost:8080/categories/addPromotionToCategory/Biotics/Easter - and you get an Json Body back with Promotion being Easter

but when you query on GET REQUEST
http://localhost:8080/categories/name/Biotics
the body JSON show that promotion is of type Christmas, even if you check and see in the H2 DB, promotion is saved as Easter...

next, you can POST REQUEST 
http://localhost:8080/store/create
bodyJson
{
{
    "storeLocation" : 
    {
        "locationCity" : "Suceava",
        "locationCountry" : "Romania",
        "locationZipCode" : 
        {
            "zipCode" : "12345"
        }
    },
    "storeType" : "MEDICAL_STORE",
    "storeName" : "Catena"
}
then GET
http://localhost:8080/store/addCategory/MEDICAL_STORE/Biotics
and in DB from Easter is becoming Christmas, but why?
