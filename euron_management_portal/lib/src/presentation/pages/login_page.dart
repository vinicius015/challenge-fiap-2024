import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/decorated_text_form_field.dart';
import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Center(
          child: Image.asset(
            'euron_logo.png',
            fit: BoxFit.cover,
          ),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            Expanded(
              child: Center(
                child: Form(
                  key: _formKey,
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      DecoratedTextFormField(label: 'Email', controller: _emailController),
                      const SizedBox(height: 25),
                      DecoratedTextFormField(label: 'Senha', controller: _passwordController),
                      const SizedBox(height: 25),
                      SizedBox(
                        width: double.infinity,
                        height: 50,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            ElevatedButton(
                              onPressed: () {},
                              style: ElevatedButton.styleFrom(
                                minimumSize: const Size(100, 50),
                                backgroundColor: euronSoftPurple,
                                elevation: 0,
                                foregroundColor: euronWhite,
                              ),
                              child: const Row(children: [
                                Text(
                                  'Login',
                                  style: TextStyle(fontSize: 20),
                                ),
                                Icon(Icons.arrow_right_alt)
                              ]),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Image.asset(
              'eurofarma_logo.png', // Caminho correto da imagem
              width: 200, // Ajuste a largura conforme necessário
            ),
          ],
        ),
      ),
    );
  }
}
