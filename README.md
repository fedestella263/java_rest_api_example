# Netlab REST API - Gestor De Compras #

Este proyecto es una prueba de una REST API, que hace el trabajo de un gestor de compras.

### Breve descripción del servicio ###

Este servicio maneja las compras de los productos. Los productos tienen
nombre, precio y cantidad en stock y pertenecen a una categoría.
Las categorías tienen nombre y un valor que indique a partir de qué cantidad
hay poco stock. Cuando la cantidad de stock de un producto es menor a dicho
valor, se registra en el producto como una bandera con valor 1, de caso contrario 0. 

También se manejan operaciones básicas POST, GET, PUT y DELETE, sobre las entidades Categorías, Productos y Compras.


### Documentación de la API ###

#### Categorías ####

* Obtener todas las categorías
* Obtener una categoría
* Crear una categoría
* Editar una categoría
* Eliminar una categoría

#### Productos ####

* Obtener todos los productos
* Obtener un producto
* Obtener las compras de un producto filtrando por la fecha
* Crear un producto
* Editar un producto
* Eliminar un producto

#### Compras ####

* Obtener todas las compras
* Obtener una compra
* Crear una compra
* Editar una compra
* Eliminar una compra


# Categorías #

**Obtener todas las categorías**
----
  Retorna un documento json con una lista de todas las categorías.

* **URL**

  /api/v1/categories

* **Método:**

  `GET`
  
*  **Parámetros URL**

   Ninguno

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 <br />
    **Contenido:** 
    ```json
    [
        {
            "id": 11,
            "lowThresholdStock": 10
        },
        {
            "id": 12,
            "lowThresholdStock": 30
        }
    ]
    ```   
* **Respuesta Error:**

   Ninguna

**Obtener una categoría**
----
  Retorna un documento json con la información de la categoría.

* **URL**

  /api/v1/categories/:id

* **Método:**

  `GET`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
        "id": 11,
        "lowThresholdStock": 10
    }
    ```
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Category was not found for parameter {id=100}",
        "fieldErrors": []
    }
    ```

**Crear una categoría**
----
  Crea una nueva categoría.

* **URL**

  /api/v1/categories

* **Método:**

  `POST`
  
*  **Parámetros URL**
	
    Ninguno

* **Parámetros Datos**
	
    **Obligatorio:**
 
   `lowThresholdStock=[integer]`
* **Respuesta Exitosa:**

  * **Código:** 201 CREATED <br />
    **Contenido:** Ninguno
* **Respuesta Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 10:13:55",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "lowThresholdStock",
                "message": "can't be null"
            }
        ]
    }
    ```

**Editar una categoría**
----
  Edita los parámetros de una categoría.

* **URL**

  /api/v1/categories/:id

* **Método:**

  `PUT`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**
	
    **Obligatorio:**
 
   `lowThresholdStock=[integer]`
* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
        "id": 11,
        "lowThresholdStock": 10
    }
    ```
* **Respuestas Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 10:13:55",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "lowThresholdStock",
                "message": "can't be null"
            }
        ]
    }
    ```
    
  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:47:57",
        "message": "Category was not found for parameter {id=110}",
        "fieldErrors": []
    }
    ```
**Eliminar una categoría**
----
  Elimina una categoría.

* **URL**

  /api/v1/categories/:id

* **Método:**

  `DELETE`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:**  Ninguno
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Category was not found for parameter {id=56}",
        "fieldErrors": []
    }
    ```

# Productos #

**Obtener todos los productos**
----
  Retorna un documento json con una lista de todos los productos.

* **URL**

  /api/v1/products

* **Método:**

  `GET`
  
*  **Parámetros URL**

   Ninguno

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    [
        {
            "id": 16,
            "name": "Mouses",
            "description": "Mouse inalámbrico Genius",
            "stock": 0,
            "price": 580,
            "lowStockFlag": 1,
            "category": {
                "id": 11,
                "lowThresholdStock": 10
            }
        },
        {
            "id": 18,
            "name": "Monitores",
            "description": "Monitores AOC 23 pulgadas",
            "stock": 50,
            "price": 1000,
            "lowStockFlag": 0,
            "category": {
                "id": 12,
                "lowThresholdStock": 5
            }
        }
    ]
    ```   
* **Respuesta Error:**

   Ninguna

**Obtener un producto**
----
  Retorna un documento json con la información de un producto.

* **URL**

  /api/v1/products/:id

* **Método:**

  `GET`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
      "id": 16,
      "name": "Mouses",
      "description": "Mouse inalámbrico Genius",
      "stock": 0,
      "price": 580,
      "lowStockFlag": 1,
      "category": {
      "id": 11,
      "lowThresholdStock": 10
    }
    ```
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Product was not found for parameter {id=256}",
        "fieldErrors": []
    }
    ```

**Obtener las compras de un producto filtrando por la fecha**
----
  Retorna un documento json con la información de todas las compras efectuadas de un producto en una determinada fecha.

* **URL**

  /api/v1/products/:id/purchases?date=:date

* **Método:**

  `GET`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`
 
   `date=[string]("yyyy-MM-dd")`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    [
        {
            "id": 11,
            "amount": 10,
            "date": "2018-02-12",
            "product": {
                "id": 20,
                "name": "Notebook Lenovo ThinkPad 13",
                "description": "Notebook para oficinas",
                "stock": 10,
                "price": 12000,
                "lowStockFlag": 0,
                "category": {
                    "id": 11,
                    "lowThresholdStock": 10
                }
            }
        }
    ]
    ```
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 01:16:37",
        "message": "Product was not found for parameter {id=23}",
        "fieldErrors": []
    }
    ```

**Crear un producto**
----
  Crea un nuevo producto y se asocia a la categoría indicada.

* **URL**

  /api/v1/products

* **Método:**

  `POST`
  
*  **Parámetros URL**
	
    Ninguno

* **Parámetros Datos**
	
    **Obligatorios:**
 
   `name=[string](min=1, max=64)`
   
   `description=[string](min=1, max=256)`
   
   `stock=[integer]`
   
   `price=[integer]`
   
   `category=[object]`
   
   `category.id=[integer]`
* **Respuesta Exitosa:**

  * **Código:** 201 CREATED <br />
    **Contenido:** Ninguno
* **Respuestas Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 11:15:47",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "price",
                "message": "can't be null"
            },
            {
                "field": "description",
                "message": "can't be blank"
            },
            {
                "field": "name",
                "message": "can't be blank"
            },
            {
                "field": "name",
                "message": "the size has to be between 1 and 64"
            },
            {
                "field": "stock",
                "message": "it has to be greater than or equal to 0"
            }
        ]
    }
    ```
    * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 11:16:46",
        "message": "Category was not found for parameter {id=10000}",
        "fieldErrors": []
    }
    ```

**Editar un producto**
----
  Edita los parámetros de un producto, también realiza actualización de la bandera de bajo stock si es necesario.

* **URL**

  /api/v1/products/:id

* **Método:**

  `PUT`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**
	
    **Obligatorios:**
 
   `name=[string](min=1, max=64)`
   
   `description=[string](min=1, max=256)`
   
   `stock=[integer]`
   
   `price=[integer]`
   
   `category=[object]`
   
   `category.id=[integer]`
* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
        "id": 20,
        "name": "Notebook Lenovo ThinkPad 13",
        "description": "Notebook para oficinas",
        "stock": 20,
        "price": 12000,
        "lowStockFlag": 0,
        "category": {
            "id": 11,
            "lowThresholdStock": 10
        }
    }
    ```
* **Respuestas Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 11:26:43",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "price",
                "message": "can't be null"
            },
            {
                "field": "stock",
                "message": "can't be null"
            }
        ]
    }
    ```
    
  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:47:57",
        "message": "Product was not found for parameter {id=56}",
        "fieldErrors": []
    }
    ```
**Eliminar un producto**
----
  Elimina un producto.

* **URL**

  /api/v1/products/:id

* **Método:**

  `DELETE`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:**  Ninguno
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Product was not found for parameter {id=25}",
        "fieldErrors": []
    }
    ```

# Compras #

**Obtener todas las compras**
----
  Retorna un documento json con una lista de todas las compras.

* **URL**

  /api/v1/purchases

* **Método:**

  `GET`
  
*  **Parámetros URL**

   Ninguno

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    [
        {
            "id": 11,
            "amount": 11,
            "date": "2018-02-12",
            "product": {
                "id": 20,
                "name": "Notebook Lenovo ThinkPad 13",
                "description": "Notebook para oficinas",
                "stock": 9,
                "price": 12000,
                "lowStockFlag": 1,
                "category": {
                    "id": 11,
                    "lowThresholdStock": 10
                }
            }
        },
        {
            "id": 12,
            "amount": 11,
            "date": "2018-02-12",
            "product": {
                "id": 21,
                "name": "Mouse Genius",
                "description": "Mouse inalámbrico Genius",
                "stock": 89,
                "price": 580,
                "lowStockFlag": 0,
                "category": {
                    "id": 11,
                    "lowThresholdStock": 10
                }
            }
        }
    ]
    ```   
* **Respuesta Error:**

   Ninguna

**Obtener una compra**
----
  Retorna un documento json con la información de una compra.

* **URL**

  /api/v1/purchases/:id

* **Método:**

  `GET`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
        "id": 11,
        "amount": 11,
        "date": "2018-02-12",
        "product": {
            "id": 20,
            "name": "Notebook Lenovo ThinkPad 13",
            "description": "Notebook para oficinas",
            "stock": 9,
            "price": 12000,
            "lowStockFlag": 1,
            "category": {
                "id": 11,
                "lowThresholdStock": 10
            }
        }
    }
    ```
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Purchase was not found for parameter {id=29}",
        "fieldErrors": []
    }
    ```

**Crear una compra**
----
  Crea una nueva compra y se asocia al producto indicada restando stock del mismo, y calculando si se activa la bandera de bajo stock.

* **URL**

  /api/v1/purchases

* **Método:**

  `POST`
  
*  **Parámetros URL**
	
    Ninguno

* **Parámetros Datos**
	
    **Obligatorios:**
   
   `amount=[integer]`
   
   `product=[object]`
   
   `product.id=[integer]`
* **Respuesta Exitosa:**

  * **Codigo:** 201 CREATED <br />
    **Contenido:** Ninguno
* **Respuestas Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 11:42:28",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "amount",
                "message": "can't be null"
            },
            {
                "field": "product",
                "message": "can't be null"
            }
        ]
    }
    ```
    * **Codigo:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 11:44:03",
        "message": "Product was not found for parameter {id=200}",
        "fieldErrors": []
    }
    ```

**Editar una compra**
----
  Edita los parámetros de una compra, y actualiza los stocks de los productos.

* **URL**

  /api/v1/purchases/:id

* **Método:**

  `PUT`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**
	
    **Obligatorios:**
   
   `amount=[integer]`
   
   `product=[object]`
   
   `product.id=[integer]`
* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:** 
    ```json
    {
        "id": 11,
        "amount": 10,
        "date": "2018-02-12",
        "product": {
            "id": 20,
            "name": "Notebook Lenovo ThinkPad 13",
            "description": "Notebook para oficinas",
            "stock": 10,
            "price": 12000,
            "lowStockFlag": 0,
            "category": {
                "id": 11,
                "lowThresholdStock": 10
            }
        }
    }
    ```
* **Respuestas Error:**

  * **Código:** 400 BAD REQUEST <br />
    **Contenido:** 
    ```json
    {
        "status": "BAD_REQUEST",
        "timestamp": "2018-02-12 01:09:03",
        "message": "Bad Arguments",
        "fieldErrors": [
            {
                "field": "amount",
                "message": "can't be null"
            },
            {
                "field": "product",
                "message": "can't be null"
            }
        ]
    }
    ```
    
  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 12:57:51",
        "message": "Purchase was not found for parameter {id=90}",
        "fieldErrors": []
    }
    ```

**Eliminar una compra**
----
  Elimina una compra, actualiza el stock del producto, y actualiza la bandera de bajo stock.

* **URL**

  /api/v1/purchases/:id

* **Método:**

  `DELETE`
  
*  **Parámetros URL**

   **Obligatorio:**
 
   `id=[integer]`

* **Parámetros Datos**

   Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 OK <br />
    **Contenido:**  Ninguno
* **Respuesta Error:**

  * **Código:** 404 NOT FOUND <br />
    **Contenido:** 
    ```json
    {
        "status": "NOT_FOUND",
        "timestamp": "2018-02-12 10:01:18",
        "message": "Purchase was not found for parameter {id=2}",
        "fieldErrors": []
    }
    ```
