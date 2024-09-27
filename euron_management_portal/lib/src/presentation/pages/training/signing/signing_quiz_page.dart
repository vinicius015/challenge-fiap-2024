import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:euron_management_portal/src/presentation/pages/training/signing/sign_document_page.dart';
import 'package:flutter/material.dart';

class QuizPage extends StatefulWidget {
  const QuizPage({super.key});

  @override
  _QuizPageState createState() => _QuizPageState();
}

class _QuizPageState extends State<QuizPage> {
  int _currentQuestionIndex = 0;
  bool _hasAnswered = false;
  int _selectedAnswerIndex = -1;

  // Exemplo de perguntas e respostas
  final List<Question> _questions = [
    Question(
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
      options: ["Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo"],
      correctAnswerIndex: 0,
    ),
    Question(
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
      options: ["Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo", "Lorem ipsum dolo"],
      correctAnswerIndex: 1,
    ),
    // Adicione mais perguntas conforme necessário
  ];

  // Função para selecionar resposta
  void _selectAnswer(int index) {
    if (_hasAnswered) return; // Previne que o usuário responda mais de uma vez
    setState(() {
      _selectedAnswerIndex = index;
      _hasAnswered = true;
    });
  }

  // Função para avançar para a próxima pergunta
  void _nextQuestion() {
    setState(() {
      _currentQuestionIndex++;
      _hasAnswered = false;
      _selectedAnswerIndex = -1;
    });
  }

  // Função para calcular o progresso
  double _calculateProgress() {
    return (_currentQuestionIndex + 1) / _questions.length;
  }

  @override
  Widget build(BuildContext context) {
    final currentQuestion = _questions[_currentQuestionIndex];

    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          // Barra de progresso
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: LinearProgressIndicator(value: _calculateProgress()),
          ),

          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text('Pergunta ${_currentQuestionIndex + 1}',
                style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
          ),

          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Text(
              currentQuestion.text,
              style: const TextStyle(fontSize: 16, color: Colors.purple),
            ),
          ),
          const SizedBox(height: 20),

          // Opções
          Expanded(
            child: ListView.builder(
              itemCount: currentQuestion.options.length,
              itemBuilder: (context, index) {
                bool isCorrect = _hasAnswered && index == currentQuestion.correctAnswerIndex;
                bool isSelected = index == _selectedAnswerIndex;

                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                  child: ElevatedButton(
                    onPressed: () => _selectAnswer(index),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _hasAnswered
                          ? (isCorrect
                              ? Colors.green
                              : isSelected
                                  ? Colors.red
                                  : Colors.grey[300])
                          : Colors.white,
                      minimumSize: const Size(double.infinity, 50),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          "${String.fromCharCode(65 + index)}. ${currentQuestion.options[index]}",
                          style: const TextStyle(fontSize: 16),
                        ),
                        if (_hasAnswered && isCorrect)
                          const Icon(Icons.check, color: Colors.white),
                        if (_hasAnswered && isSelected && !isCorrect)
                          const Icon(Icons.close, color: Colors.white),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),

          // Botão para avançar
          if (_hasAnswered)
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: ElevatedButton(
                onPressed: _currentQuestionIndex + 1 < _questions.length
                    ? _nextQuestion
                    : () {
                        // Termina o quiz ou navega para outra página
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(content: Text("Quiz finalizado!")),
                        );

                        Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      const SignDocumentPage()));
                      },
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(double.infinity, 50),
                iconColor: Colors.blue,
                ),
                child: Text(_currentQuestionIndex + 1 < _questions.length
                    ? 'Próxima Pergunta'
                    : 'Finalizar'),
              ),
            ),
        ],
      ),
    );
  }
}

class Question {
  final String text;
  final List<String> options;
  final int correctAnswerIndex;

  Question({
    required this.text,
    required this.options,
    required this.correctAnswerIndex,
  });
}
