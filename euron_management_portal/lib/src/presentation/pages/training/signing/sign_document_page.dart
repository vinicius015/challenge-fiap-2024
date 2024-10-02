import 'package:euron_management_portal/src/config/globals.dart';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'package:pdf/widgets.dart' as pw; // Para criar PDF
import 'package:signature/signature.dart'; // Para capturar a assinatura
import 'package:permission_handler/permission_handler.dart'; // Para gerenciar permissões
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';

class Document {
  final String title;
  final String body;

  Document({required this.title, required this.body});
}

class SignDocumentPage extends StatefulWidget {
  final Document? document;

  const SignDocumentPage({super.key, this.document});

  @override
  SignDocumentPageState createState() => SignDocumentPageState();
}

class SignDocumentPageState extends State<SignDocumentPage> {
  final SignatureController _signatureController = SignatureController(
    penStrokeWidth: 3.0,
    penColor: Colors.black,
    exportBackgroundColor: Colors.white,
  );

  // Utiliza o documento passado se houver, senão utiliza um exemplo
  final Document exampleDocument = Document(
    title: 'Certificado de Conclusão de Treinamento',
    body:
        'Certificamos que o funcionário [NOME DO FUNCIONÁRIO], portador do CPF [CPF DO FUNCIONÁRIO], '
        'participou com êxito do treinamento intitulado "[TÍTULO DO TREINAMENTO]" realizado entre [DATA DE INÍCIO] e [DATA DE TÉRMINO]. '
        'Durante o treinamento, o funcionário demonstrou comprometimento e adquiriu os conhecimentos necessários para a sua função. '
        'Este certificado é emitido como prova de sua participação e pode ser apresentado para fins de registro ou avaliação. '
        'Agradecemos pela dedicação e desejamos sucesso em suas atividades profissionais.',
  );

  bool _isLoading = false;

  // Função para gerar o PDF
  Future<void> _generatePdfWithSignature() async {
    try {
      setState(() {
        _isLoading = true;
      });

      // Verificar se o usuário concedeu permissão para armazenar
      var status = await Permission.storage.request();
      if (status.isGranted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
              content: Text('Permissão negada para armazenar o arquivo.')),
        );
        setState(() {
          _isLoading = false;
        });
        return;
      }

      // Gerar a assinatura como uma imagem
      final signature = await _signatureController.toPngBytes();
      if (signature == null) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Por favor, insira sua assinatura.')),
        );
        setState(() {
          _isLoading = false;
        });
        return;
      }

      // Criar o documento PDF
      final pdf = pw.Document();
      pdf.addPage(
        pw.Page(
          build: (pw.Context context) {
            return pw.Column(
              children: [
                pw.Text(exampleDocument.title,
                    style: pw.TextStyle(
                        fontSize: 24, fontWeight: pw.FontWeight.bold)),
                pw.Spacer(),
                pw.Text(exampleDocument.body,
                    style: pw.TextStyle(fontSize: 18)),
                pw.Spacer(),
                // Adicionar a assinatura no canto inferior direito
                pw.Align(
                  alignment: pw.Alignment.bottomRight,
                  child: pw.Image(pw.MemoryImage(signature),
                      width: 150, height: 75),
                ),
              ],
            );
          },
        ),
      );

      // Obter o diretório de download e salvar o PDF
      final directory = await getExternalStorageDirectory();
      final path = "${directory?.path}/document_assinado.pdf";
      final file = File(path);
      await file.writeAsBytes(await pdf.save());

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Documento salvo em: $path')),
      );
    } catch (e) {
      print('Erro ao gerar o PDF: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Erro ao gerar o documento.')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  // Função para confirmar a assinatura
  void _confirmSignature() async {
    bool confirmed = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Confirmação'),
          content: const Text('Você confirma que leu e assinou o documento?'),
          actions: <Widget>[
            TextButton(
              onPressed: () => Navigator.of(context).pop(false),
              child: const Text('Cancelar'),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(true),
              child: const Text('Confirmar'),
            ),
          ],
        );
      },
    );

    if (confirmed) {
      await _generatePdfWithSignature();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            // Exibir o título e o corpo do documento
            Text(
              exampleDocument.title,
              style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Expanded(
              child: SingleChildScrollView(
                child: Text(
                  exampleDocument.body,
                  style: const TextStyle(fontSize: 18),
                ),
              ),
            ),
            const SizedBox(height: 20),
            const Text(
              'Assine abaixo:',
              style: TextStyle(fontSize: 18),
            ),
            const SizedBox(height: 5),
            Container(
              decoration: BoxDecoration(
                border: Border.all(color: Colors.black),
              ),
              height: 130,
              child: Signature(
                controller: _signatureController,
                backgroundColor: Colors.white,
              ),
            ),
            const SizedBox(height: 10),
            ElevatedButton(
              onPressed: () => _signatureController.clear(),
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(400, 55),
                backgroundColor: euronSoftPurple,
                foregroundColor: euronWhite,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(4.0),
                ),
              ),
              child: const Text('Limpar assinatura',
                  style: TextStyle(fontSize: 20)),
            ),
            const SizedBox(height: 10),
            // Botão de confirmação
            ElevatedButton(
              onPressed: _confirmSignature,
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(400, 55),
                backgroundColor: euronSoftPurple,
                foregroundColor: euronWhite,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(4.0),
                ),
              ),
              child: _isLoading
                  ? const CircularProgressIndicator(color: Colors.white)
                  : const Text('Confirmar Assinatura',
                      style: TextStyle(fontSize: 20)),
            ),
          ],
        ),
      ),
    );
  }
}
