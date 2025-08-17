from flask import request, jsonify, Blueprint
from sqlalchemy.exc import IntegrityError
from app.models import Product, Inventory, db

api = Blueprint('api', __name__)
@api.route('/products', methods=['POST'])
def create_product():
    # 1. Use try-except for robust error handling.
    try:
        # 2. Add data validation and safer access using .get()
        data = request.json
        if not data:
            return jsonify({"error": "Request body must be JSON"}), 400

        name = data.get('name')
        sku = data.get('sku')
        price = data.get('price')
        warehouse_id = data.get('warehouse_id')
        initial_quantity = data.get('initial_quantity', 0)  # Default quantity to 0

        # 3. Check for required fields and validate data types
        if not all([name, sku, price, warehouse_id]) or not isinstance(initial_quantity, (int, float)):
            return jsonify({"error": "Missing required fields or invalid data type"}), 400

        # 4. Check for unique SKU before creating the product
        if Product.query.filter_by(sku=sku).first():
            return jsonify({"error": f"Product with SKU '{sku}' already exists."}), 409 # 409 Conflict

        # 5. Create new product instance.
        #    Use a single transaction for both product and inventory.
        product = Product(
            name=name,
            sku=sku,
            price=price,
            warehouse_id=warehouse_id
        )
        db.session.add(product)
        # Note: Do NOT commit yet

        # 6. Create inventory record
        inventory = Inventory(
            product=product,  # Use the object relationship for clarity
            warehouse_id=warehouse_id,
            quantity=initial_quantity
        )
        db.session.add(inventory)

        # 7. Commit both additions in a single, atomic transaction
        db.session.commit()

        # 8. Return a 201 Created status code for success
        return jsonify({"message": "Product created successfully", "product_id": product.id}), 201

    except IntegrityError:
        # 9. Handle database transaction failures gracefully
        db.session.rollback()  # Rollback in case of a unique constraint violation or other DB error
        return jsonify({"error": "Database integrity error. Check your data."}), 500

    except Exception as e:
        db.session.rollback()
        # 10. General catch-all for unexpected errors
        return jsonify({"error": f"An unexpected error occurred: {str(e)}"}), 500
