import 'package:euron_management_portal/src/presentation/components/bottom_nav_bar.dart';
import 'package:flutter/material.dart';
import 'package:euron_management_portal/src/config/globals.dart';

class AuthConfirmationPage extends StatefulWidget {
  const AuthConfirmationPage({super.key});

  @override
  State<AuthConfirmationPage> createState() => _AuthConfirmationPageState();
}

class _AuthConfirmationPageState extends State<AuthConfirmationPage> {
  final List<TextEditingController> _controllers =
      List.generate(4, (_) => TextEditingController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        backgroundColor: euronDarkBlue,
        title: Image.asset(
          'assets/euron_logo.png',
          width: 100,
        ),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Verifique sua Identidade',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 20),
            const Text(
              'Insira o código enviado ao seu número',
              style: TextStyle(fontSize: 16),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: const [
                Icon(
                  Icons.key,
                  size: 40,
                  color: Colors.green,
                ),
              ],
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: List.generate(4, (index) {
                return SizedBox(
                  width: 50,
                  height: 50,
                  child: TextField(
                    controller: _controllers[index],
                    keyboardType: TextInputType.number,
                    textAlign: TextAlign.center,
                    maxLength: 1, // Limite de 1 caractere
                    style: const TextStyle(fontSize: 24),
                    decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      counterText: "", // Remove o contador
                    ),
                    onChanged: (value) {
                      if (value.length == 1 && index < 3) {
                        FocusScope.of(context)
                            .nextFocus(); // Muda para o próximo campo automaticamente
                      }
                      if (value.isEmpty && index > 0) {
                        FocusScope.of(context)
                            .previousFocus(); // Volta ao campo anterior se estiver vazio
                      }
                    },
                  ),
                );
              }),
            ),
            const SizedBox(height: 30),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const BottomNavBar(
                              initialPage: 0, isAdmin: false)));
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                ),
                child: const Text(
                  'Enviar',
                  style: TextStyle(fontSize: 18, color: euronWhite),
                ),
              ),
            ),
            const SizedBox(height: 30),
            Image.asset(
              'assets/eurofarma_logo.png',
              width: 150,
            ),
          ],
        ),
      ),
    );
  }
}
