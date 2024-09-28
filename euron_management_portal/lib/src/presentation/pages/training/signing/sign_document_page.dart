import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart'; // Import necessário para abrir o navegador

class SignDocumentPage extends StatelessWidget {
  const SignDocumentPage({super.key});

  // Função para abrir o link do DocuSign

  void _signDocument() async {
    
    Uri url = Uri.parse('https://www.docusign.com');

    if (await canLaunchUrl(url)) {
      await launchUrl(url,
          mode: LaunchMode.externalApplication); // Navegador externo
    } else {
      throw 'Could not launch $url';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const Text(
                'Assine o documento referente ao treinamento',
                style: TextStyle(fontSize: 20, color: Colors.black),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 40),
              ElevatedButton(
                onPressed: _signDocument, // Redireciona para o DocuSign
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(400, 60),
                  backgroundColor: Colors.blue, // Cor do botão
                  elevation: 0,
                  foregroundColor: Colors.white,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(4.0),
                  ),
                ),
                child: const Text('Assinar documento',
                    style: TextStyle(fontSize: 20)),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
