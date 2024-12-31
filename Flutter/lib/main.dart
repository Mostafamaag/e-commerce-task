import 'package:flutter/material.dart';
import 'customer_page.dart';
import 'cart_page.dart';
import 'store_page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: '/',
      routes: {
        '/store': (context) => StorePage(),
        '/login': (context) => LoginPage(),
        '/cart': (context) => CartPage(),
      },
      debugShowCheckedModeBanner: false,
      title: 'E-commerce App',
      home: HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Ecommerce App'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => CustomerPage()),
                );
              },
              child: Text('Customer'),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Vendor Page Coming Soon!')),
                );
              },
              child: Text('Vendor'),
            ),
          ],
        ),
      ),
    );
  }
}
